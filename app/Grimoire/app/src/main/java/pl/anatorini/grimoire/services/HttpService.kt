package pl.anatorini.grimoire.services

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import pl.anatorini.grimoire.models.Alignment
import pl.anatorini.grimoire.models.Background
import pl.anatorini.grimoire.models.CharacterClass
import pl.anatorini.grimoire.models.Item
import pl.anatorini.grimoire.models.PaginatedResponse
import pl.anatorini.grimoire.models.Race
import pl.anatorini.grimoire.models.Spell
import pl.anatorini.grimoire.state.Auth
import pl.anatorini.grimoire.state.Settings
import java.util.concurrent.TimeoutException

class HttpService(private val auth: Auth, private val settings: Settings) {
    fun login() {
        Log.println(Log.INFO, "", "$auth")
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getModel(
        modelUrl: String,
        pageUrl: String?
    ): PaginatedResponse<T> {
        Log.println(Log.INFO, "HttpService", "getSpells()")
        var jsonResponse: String = ""
        val client: HttpClient = HttpClient(CIO)
        val path = pageUrl ?: "http://${settings.serverAddress}:${settings.port}/$modelUrl"
        try {
            val response = client.get(path)
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

}