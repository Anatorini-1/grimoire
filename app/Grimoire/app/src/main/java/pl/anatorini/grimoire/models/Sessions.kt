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
data class Session(
    override val url: String,
    var campaign: CampaignForeignField = CampaignForeignField(""),
    var active: Boolean = false,
    var created_at: String = "",
    var ended_at: String? = null,
    var messages: List<CampaignMessageForeignField> = emptyList(),
    var token: String? = null
) : Model

@Serializable
data class RouteObject(
    val name: String
)

@Serializable(with = SessionForeignFieldSerializer::class)
data class SessionForeignField(
    override var url: String,
    override var value: Session? = null,
    override val getValues: suspend () -> List<Session> = {
        var paginatedResponse = HttpService.getSessions(null)
        val items = ArrayList<Session>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getSessions(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    },
    override val getValue: suspend () -> Session = {
        HttpService.getModelInstance(url)
    }

) : UnnamedForeignField<Session>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = SessionForeignField::class)
object SessionForeignFieldSerializer : KSerializer<SessionForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: SessionForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): SessionForeignField {
        val url = decoder.decodeString()
        return SessionForeignField(url = url ?: "")
    }
}
