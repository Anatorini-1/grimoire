package pl.anatorini.grimoire.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import pl.anatorini.grimoire.services.HttpService

@Serializable
data class User(
    var username: String,
    var token: String,
)

@Serializable
data class LoginData(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String
)

@Serializable
data class RegisterData(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class Player(
    override val name: String,
    override val url: String,
    val email: String,

    ) : NamedModel


@Serializable(with = PlayerForeignFieldSerializer::class)
data class PlayerForeignField(
    override var url: String,
    override var value: Player? = null,
    override val getValues: suspend () -> List<Player> = {
        var paginatedResponse = HttpService.getPlayers(null)
        val items = ArrayList<Player>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getPlayers(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    },
    override val getValue: suspend () -> Player = {
        HttpService.getModelInstance(url)
    }
) : ForeignField<Player> {
    override fun toString(): String = url
}


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PlayerForeignField::class)
object PlayerForeignFieldSerializer : KSerializer<PlayerForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: PlayerForeignField) {
        encoder.encodeString(value.url)
    }

    override fun deserialize(decoder: Decoder): PlayerForeignField {
        val url: String? = decoder.decodeString()
        return PlayerForeignField(url = url ?: "")
    }
}

