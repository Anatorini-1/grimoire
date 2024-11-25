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
import kotlinx.serialization.encoding.encodeStructure
import pl.anatorini.grimoire.services.HttpService

@Serializable
data class Race(
    override var name: String = "",
    override var url: String = ""
) : Model


@Serializable
data class PostRace(
    override val name: String
) : PostModel


@Serializable(with = RaceForeignFieldSerializer::class)
data class RaceForeignField(
    override var url: String,
    override var value: Race? = null,
    override val getValues: suspend () -> List<Race> = {
        var paginatedResponse = HttpService.getRaces(null)
        val items = ArrayList<Race>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getRaces(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Race>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = RaceForeignField::class)
object RaceForeignFieldSerializer : KSerializer<RaceForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: RaceForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): RaceForeignField {
        val url = decoder.decodeString()
        return RaceForeignField(url = url ?: "")
    }
}
