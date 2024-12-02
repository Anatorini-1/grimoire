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
data class CampaignMessage(
    override var url: String,
    var session: SessionForeignField,
    var sender: CampaignPlayer,
    var message: String,
    var created_at: String,
) : Model


@Serializable
data class PostCampaignMessage(
    val message: String
)

@Serializable(with = CampaignMessageForeignFieldSerializer::class)
data class CampaignMessageForeignField(
    override var url: String,
    override var value: CampaignMessage? = null,
    override val getValues: suspend () -> List<CampaignMessage> = {
        var paginatedResponse = HttpService.getCampaignMessages(null)
        val items = ArrayList<CampaignMessage>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getCampaignMessages(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    },
    override val getValue: suspend () -> CampaignMessage = {
        HttpService.getModelInstance(url)
    }

) : UnnamedForeignField<CampaignMessage>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = CampaignMessageForeignField::class)
object CampaignMessageForeignFieldSerializer : KSerializer<CampaignMessageForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: CampaignMessageForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): CampaignMessageForeignField {
        val url = decoder.decodeString()
        return CampaignMessageForeignField(url = url ?: "")
    }
}
