package pl.anatorini.grimoire.services

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
import pl.anatorini.grimoire.models.CharacterClass
import pl.anatorini.grimoire.models.Item
import pl.anatorini.grimoire.models.LoginData
import pl.anatorini.grimoire.models.LoginResponse
import pl.anatorini.grimoire.models.Model
import pl.anatorini.grimoire.models.PaginatedResponse
import pl.anatorini.grimoire.models.PostAlignment
import pl.anatorini.grimoire.models.PostBackground
import pl.anatorini.grimoire.models.PostCharacterClass
import pl.anatorini.grimoire.models.PostItem
import pl.anatorini.grimoire.models.PostModel
import pl.anatorini.grimoire.models.PostRace
import pl.anatorini.grimoire.models.PostSpell
import pl.anatorini.grimoire.models.PostStatistic
import pl.anatorini.grimoire.models.Race
import pl.anatorini.grimoire.models.RegisterData
import pl.anatorini.grimoire.models.Spell
import pl.anatorini.grimoire.models.Statistic
import pl.anatorini.grimoire.models.User
import pl.anatorini.grimoire.state.Auth
import pl.anatorini.grimoire.state.Settings
import java.util.concurrent.TimeoutException

class HttpService(private val auth: Auth, private val settings: Settings) {
    companion object {
        var address = "192.168.1.27";
        var port = 8000;
        var user: User? = null

        private suspend inline fun <reified T : Model> getModel(
            modelUrl: String,
            pageUrl: String?
        ): PaginatedResponse<T> {
            Log.println(Log.INFO, "HttpService", "getSpells()")
            var jsonResponse: String = ""
            val client: HttpClient = HttpClient(CIO)
            val path = pageUrl ?: "http://${address}:${port}$modelUrl/"
            try {
                val response = client.get(path){
                    if(user != null){
                        header("Authorization", "Bearer ${user!!.token}")
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
                    if(user != null){
                        header("Authorization", "Bearer ${user!!.token}")
                    }
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
                val item: R = Json.decodeFromString(jsonResponse)
                return item
            } catch (e: Error) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            } catch (e: Exception) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                throw e
            }
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


        val postClass: suspend (instance: CharacterClass) -> CharacterClass? = { it
            postModel("/classes", PostCharacterClass(it.name, it.spellcastingAbility))
        }
        val postAlignment:suspend (instance: Alignment) -> Alignment? = { it
            postModel("/alignments", PostAlignment(name=it.name))
        }
        val postBackground:suspend (instance: Background) -> Background? = { it
            postModel("/backgrounds", PostBackground(name=it.name))
        }

        val postItem:suspend (instance: Item) -> Item? = {
            postModel("/items", PostItem(
                name=it.name,
                weight=it.weight,
                value=it.value,
                weapon=it.weapon,
                attackBonus=it.attackBonus,
                damage=it.damage,
                description=it.description,
            ))
        }

        val postRace:suspend (instance: Race) -> Race? = { it
            postModel("/races", PostRace(name=it.name))
        }

        val postSpell:suspend (instance: Spell) -> Spell? = { it
            postModel("/spells", PostSpell(
                name=it.name,
                description=it.description,
                level=it.level,
                ritual=it.ritual,
                range=it.range,
                duration=it.duration,
                school=it.school,
                ))
        }

        val postStatistic:suspend (instance: Statistic) -> Statistic? = { it
            postModel("/statistics", PostStatistic(name=it.name))
        }

        val login: suspend (login:String, password:String) -> Boolean = {login, password ->

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
                    setBody(LoginData(login,password))
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
                Log.println(Log.INFO, "HttpService-PostModelResponse", jsonResponse)
                val token: LoginResponse = Json.decodeFromString(jsonResponse)
                user = User(login,token.token)
                true
            } catch (e: Error) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                false
            } catch (e: Exception) {
                Log.println(Log.ERROR, "HttpService", e.message.orEmpty())
                false
            }
        }

        val logout: suspend () -> Boolean = {
            user = null
            true
        }

        val register: suspend(login:String,email:String,password:String) -> Boolean = { login, email, password ->
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
                    setBody(RegisterData(login,email,password))
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