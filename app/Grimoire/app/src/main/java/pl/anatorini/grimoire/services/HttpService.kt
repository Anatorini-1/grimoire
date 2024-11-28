package pl.anatorini.grimoire.services

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import pl.anatorini.grimoire.models.Alignment
import pl.anatorini.grimoire.models.Background
import pl.anatorini.grimoire.models.Campaign
import pl.anatorini.grimoire.models.CampaignMessage
import pl.anatorini.grimoire.models.CampaignPlayer
import pl.anatorini.grimoire.models.Character
import pl.anatorini.grimoire.models.CharacterClass
import pl.anatorini.grimoire.models.Item
import pl.anatorini.grimoire.models.LoginData
import pl.anatorini.grimoire.models.LoginResponse
import pl.anatorini.grimoire.models.Model
import pl.anatorini.grimoire.models.NamedModel
import pl.anatorini.grimoire.models.PaginatedResponse
import pl.anatorini.grimoire.models.Player
import pl.anatorini.grimoire.models.PostAlignment
import pl.anatorini.grimoire.models.PostBackground
import pl.anatorini.grimoire.models.PostCampaignMessage
import pl.anatorini.grimoire.models.PostCharacter
import pl.anatorini.grimoire.models.PostCharacterClass
import pl.anatorini.grimoire.models.PostItem
import pl.anatorini.grimoire.models.PostModel
import pl.anatorini.grimoire.models.PostRace
import pl.anatorini.grimoire.models.PostSpell
import pl.anatorini.grimoire.models.PostStatistic
import pl.anatorini.grimoire.models.Race
import pl.anatorini.grimoire.models.RegisterData
import pl.anatorini.grimoire.models.Session
import pl.anatorini.grimoire.models.Spell
import pl.anatorini.grimoire.models.Statistic
import pl.anatorini.grimoire.models.UnnamedPaginatedResponse
import pl.anatorini.grimoire.models.User
import pl.anatorini.grimoire.models.WebSocketHandler
import pl.anatorini.grimoire.state.Auth
import pl.anatorini.grimoire.state.Settings
import java.util.concurrent.TimeoutException

class HttpService(private val auth: Auth, private val settings: Settings) {
    companion object {
        var address = "192.168.0.55";
        var port = 8000;
        var user: User? = User(
            token = "66365a3edf9b42943d862eaf6033eaf4be927883",
            username = "admin"
        )
        var wssClient: HttpClient? = null

        private suspend inline fun <reified T : Model> getUnnamedModel(
            modelUrl: String,
            pageUrl: String?,
            queryParams: List<Pair<String, String>> = emptyList()
        ): UnnamedPaginatedResponse<T> {
            Log.println(Log.INFO, "HttpService", "getSpells()")
            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO)
            val path = pageUrl ?: "http://${address}:${port}$modelUrl/"
            try {
                val response = client.get(path) {
                    if (user != null) {
                        header("Authorization", "Bearer ${user!!.token}")
                    }
                    for (p in queryParams) {
                        url {
                            parameters.append(p.first, p.second)
                        }
                    }
                }
                when (response.status) {
                    HttpStatusCode.OK -> {
                        when (response.contentType()) {
                            ContentType.Application.Json -> {
                                Log.println(Log.INFO, "HttpService", "$path OK")
                                Log.println(Log.INFO, "HttpService", response.body<String>())
                                jsonResponse = response.body()
                            }

                            else -> {
                                Log.println(
                                    Log.ERROR,
                                    "HttpService",
                                    response.contentType().toString()
                                )
                                Log.println(Log.ERROR, "HttpService", response.body())
                            }
                        }
                    }

                    else -> {
                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }
                }
                client.close()
            } catch (e: TimeoutException) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
            }

            try {
                val items: UnnamedPaginatedResponse<T> = Json.decodeFromString(jsonResponse)
                return items
            } catch (e: Error) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            }
        }

        private suspend inline fun <reified T : NamedModel> getModel(
            modelUrl: String,
            pageUrl: String?,
            queryParams: List<Pair<String, String>> = emptyList()
        ): PaginatedResponse<T> {
            Log.println(Log.INFO, "HttpService", "getSpells()")
            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO)
            val path = pageUrl ?: "http://${address}:${port}$modelUrl/"
            try {
                val response = client.get(path) {
                    if (user != null) {
                        header("Authorization", "Bearer ${user!!.token}")
                        url {
                            for (p in queryParams) {
                                parameters.append(p.first, p.second)
                            }
                        }
                    }
                }
                when (response.status) {
                    HttpStatusCode.OK -> {
                        when (response.contentType()) {
                            ContentType.Application.Json -> {
                                Log.println(Log.INFO, "HttpService", "$path OK")
                                Log.println(Log.INFO, "HttpService", response.body<String>())
                                jsonResponse = response.body()
                            }

                            else -> {
                                Log.println(
                                    Log.ERROR,
                                    "HttpService",
                                    response.contentType().toString()
                                )
                                Log.println(Log.ERROR, "HttpService", response.body())
                            }
                        }
                    }

                    else -> {
                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }
                }
                client.close()
            } catch (e: TimeoutException) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
            }

            try {
                val items: PaginatedResponse<T> = Json.decodeFromString(jsonResponse)
                return items
            } catch (e: Error) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            }
        }

        private suspend inline fun <reified T : PostModel, reified R : Model> postModel(
            modelPath: String,
            instance: T
        ): R? {
            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }
            val path = "http://${address}:${port}/$modelPath/"
            try {
                val response = client.post(path) {
                    contentType(ContentType.Application.Json)
                    setBody(instance)
                    if (user != null) {
                        header("Authorization", "Bearer ${user!!.token}")
                    }
                }
                when (response.status) {
                    HttpStatusCode.Created -> {
                        when (response.contentType()) {
                            ContentType.Application.Json -> {
//                                Log.println(Log.INFO, "HttpService", "$path OK")
//                                Log.println(Log.INFO, "HttpService", response.body<String>())
                                jsonResponse = response.body()
                            }

                            else -> {
//                                Log.println(
//                                    Log.ERROR,
//                                    "HttpService",
//                                    response.contentType().toString()
//                                )
//                                Log.println(Log.ERROR, "HttpService", response.body())
                            }
                        }
                    }

                    else -> {
//                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }
                }
                client.close()
            } catch (e: TimeoutException) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
            }
            try {
//                Log.println(Log.INFO, "HttpService-PostModelResponse", jsonResponse)
                val item: R = Json.decodeFromString(jsonResponse)
                return item
            } catch (e: Error) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            } catch (e: Exception) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            }
        }

        suspend inline fun <reified T : Model> getModelInstance(
            instanceUrl: String,
        ): T {
//            Log.println(Log.INFO, "HttpService", "getSpells()")
            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO)
            val path = instanceUrl
            try {
                val response = client.get(path) {
                    if (user != null) {
                        header("Authorization", "Bearer ${user!!.token}")
                    }
                }
                when (response.status) {
                    HttpStatusCode.OK -> {
                        when (response.contentType()) {
                            ContentType.Application.Json -> {
//                                Log.println(Log.INFO, "HttpService", "$path OK")
//                                Log.println(Log.INFO, "HttpService", response.body<String>())
                                jsonResponse = response.body()
                            }

                            else -> {
//                                Log.println(
//                                    Log.ERROR,
//                                    "HttpService",
//                                    response.contentType().toString()
//                                )
//                                Log.println(Log.ERROR, "HttpService", response.body())
                            }
                        }
                    }

                    else -> {
//                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }
                }
                client.close()
            } catch (e: TimeoutException) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
            }

            try {
                val item: T = Json.decodeFromString(jsonResponse)
                return item
            } catch (e: Error) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            }
        }

        suspend fun wssConnect(handler: WebSocketHandler, token: String) {
            wssClient = HttpClient {
                install(WebSockets)
            }
            wssClient!!.wss("ws://192.168.0.55:8000/ws/session/$token/?token=${user?.token}") {
                Log.d("WSS", "Conencted")
                handler.onConnected()
                try {
                    for (frame in incoming) {
                        handler.onMessage(frame)
                    }
                    handler.onDisconnected()
                } catch (e: Exception) {
                    Log.d("WSS", "Connection error")
                    handler.onError
                }
            }
        }

        fun wssDisconnect() {
            wssClient?.close()
        }


        val getSpells: suspend (pageUrl: String?) -> PaginatedResponse<Spell> = {
            getModel("/spells", it)
        }

        val getItems: suspend (pageUrl: String?) -> PaginatedResponse<Item> = {
            getModel("/items", it)
        }

        val getClasses: suspend (pageUrl: String?) -> PaginatedResponse<CharacterClass> = {
            getModel("/classes", it)
        }
        val getRaces: suspend (pageUrl: String?) -> PaginatedResponse<Race> = {
            getModel("/races", it)
        }
        val getAlignments: suspend (pageUrl: String?) -> PaginatedResponse<Alignment> = {
            getModel("/alignments", it)
        }
        val getBackgrounds: suspend (pageUrl: String?) -> PaginatedResponse<Background> = {
            getModel("/backgrounds", it)
        }

        val getStatistics: suspend (pageUrl: String?) -> PaginatedResponse<Statistic> = {
            getModel("/statistics", it)
        }
        val getCharacters: suspend (pageUrl: String?) -> PaginatedResponse<Character> = {
            getModel("/characters", it)
        }

        val getPlayers: suspend (pageUrl: String?) -> PaginatedResponse<Player> = {
            getModel("/users", it)
        }

        val getCampaigns: suspend (pageUrl: String?) -> PaginatedResponse<Campaign> = {
            getModel("/campaigns", it)
        }

        val getCampaignMessages: suspend (pageUrl: String?) -> UnnamedPaginatedResponse<CampaignMessage> =
            {
                getUnnamedModel("/sessionChatMessages", it)
            }
        val getSessions: suspend (pageUrl: String?) -> UnnamedPaginatedResponse<Session> = {
            getUnnamedModel("/sessions", it)
        }


        val getCampaignPlayers: suspend (campaign_id: String?, pageUrl: String?) -> UnnamedPaginatedResponse<CampaignPlayer> =
            { campaign_id, pageUrl ->
                getUnnamedModel(
                    "/campaignPlayers",
                    pageUrl,
                    listOf("campaign" to (campaign_id ?: ""))
                )
            }
//        val: suspend (campaign: String?, pageUrl: String?) -> UnnamedPaginatedResponse<Campaign> =
//            { campaign_id, pageUrl ->
//                getUnnamedModel(
//                    "/campaignMessages",
//                    pageUrl,
//                    listOf("campaign" to (campaign_id ?: ""))
//                )
//            }


        val postClass: suspend (instance: CharacterClass) -> CharacterClass? = {
            postModel("/classes", PostCharacterClass(it.name, it.spellcastingAbility))
        }
        val postAlignment: suspend (instance: Alignment) -> Alignment? = {
            it
            postModel("/alignments", PostAlignment(name = it.name))
        }
        val postBackground: suspend (instance: Background) -> Background? = {
            it
            postModel("/backgrounds", PostBackground(name = it.name))
        }

        val postItem: suspend (instance: Item) -> Item? = {
            postModel(
                "/items", PostItem(
                    name = it.name,
                    weight = it.weight,
                    value = it.value,
                    weapon = it.weapon,
                    attackBonus = it.attackBonus,
                    damage = it.damage,
                    description = it.description,
                )
            )
        }

        val postRace: suspend (instance: Race) -> Race? = {
            it
            postModel("/races", PostRace(name = it.name))
        }

        val postSpell: suspend (instance: Spell) -> Spell? = {
            it
            postModel(
                "/spells", PostSpell(
                    name = it.name,
                    description = it.description,
                    level = it.level,
                    ritual = it.ritual,
                    range = it.range,
                    duration = it.duration,
                    school = it.school,
                )
            )
        }

        val postStatistic: suspend (instance: Statistic) -> Statistic? = {
            it
            postModel("/statistics", PostStatistic(name = it.name))
        }

        val postCharacter: suspend (instance: Character) -> Character? = {
            it
            postModel(
                "/statistics", PostCharacter(
                    name = it.name,
                    player = it.player,
                    classname = it.classname,
                    experience = it.experience,
                    info = it.info,
                    background = it.background,
                    alignment = it.alignment,
                    race = it.race,
                    deathSaveSuccess = it.deathSaveSuccess,
                    deathSaveFailure = it.deathSaveFailure,
                    temporaryHitpoint = it.temporaryHitpoint,
                    items = it.items,
                    statistics = it.statistics,
                    skills = it.skills,
                    equipment = it.equipment,
                    spells = it.spells,
                )
            )
        }


        val sendChatMessage: suspend (session_url: String, message: String) -> Unit = { url, msg ->
            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }
            try {
                val response = client.post("${url}send-message/") {
                    contentType(ContentType.Application.Json)
                    setBody(PostCampaignMessage(msg))
                    if (user != null) {
                        header("Authorization", "Bearer ${user!!.token}")
                    }
                }
                when (response.status) {
                    HttpStatusCode.Created -> {
                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }

                    else -> {
                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }
                }
                client.close()
            } catch (e: TimeoutException) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
            }
        }
        val login: suspend (login: String, password: String) -> Boolean = { login, password ->

            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }
            val path = "http://${address}:${port}/login/"
            try {
                val response = client.post(path) {
                    contentType(ContentType.Application.Json)
                    setBody(LoginData(login, password))
                }
                when (response.status) {
                    HttpStatusCode.OK -> {
                        when (response.contentType()) {
                            ContentType.Application.Json -> {
//                                Log.println(Log.INFO, "HttpService", "$path OK")
//                                Log.println(Log.INFO, "HttpService", response.body<String>())
                                jsonResponse = response.body()
                            }

                            else -> {
//                                Log.println(
//                                    Log.ERROR,
//                                    "HttpService",
//                                    response.contentType().toString()
//                                )
//                                Log.println(Log.ERROR, "HttpService", response.body())
                            }
                        }
                    }

                    else -> {
//                        Log.println(Log.ERROR, "HttpService", response.status.description)
                    }
                }
                client.close()
            } catch (e: TimeoutException) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
            }
            try {
//                Log.println(Log.INFO, "HttpService-PostModelResponse", jsonResponse)
                val token: LoginResponse = Json.decodeFromString(jsonResponse)
                user = User(login, token.token)
                true
            } catch (e: Error) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                false
            } catch (e: Exception) {
//                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                false
            }
        }

        val logout: suspend () -> Boolean = {
            user = null
            true
        }

        val register: suspend (login: String, email: String, password: String) -> Boolean =
            { login, email, password ->
                var jsonResponse: String = ""
                val client: HttpClient = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json()
                    }
                }
                val path = "http://${address}:${port}/register/"
                try {
                    val response = client.post(path) {
                        contentType(ContentType.Application.Json)
                        setBody(RegisterData(login, email, password))
                    }
                    when (response.status) {
                        HttpStatusCode.Created -> {
                            when (response.contentType()) {
                                ContentType.Application.Json -> {
                                    Log.println(Log.INFO, "HttpService", "$path OK")
                                    Log.println(Log.INFO, "HttpService", response.body<String>())
                                    jsonResponse = response.body()
                                }

                                else -> {
                                    Log.println(
                                        Log.ERROR,
                                        "HttpService",
                                        response.contentType().toString()
                                    )
                                    Log.println(Log.ERROR, "HttpService", response.body())
                                }
                            }
                        }

                        else -> {
                            Log.println(Log.ERROR, "HttpService", response.status.description)
                        }
                    }
                    client.close()
                } catch (e: TimeoutException) {
                    Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                }
                try {
                    Log.println(Log.INFO, "HttpService-PostModelResponse", jsonResponse)
                    true
                } catch (e: Error) {
                    Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                    false
                } catch (e: Exception) {
                    Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                    false
                }

            }


    }
}