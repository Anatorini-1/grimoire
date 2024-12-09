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
data class Campaign(
    override var name: String = "",
    override var url: String = "",
    var dm: Player? = null,
    var players: List<CampaignPlayer> = emptyList(),
    var sessions: List<Session> = emptyList(),
    var accepted: Boolean = false
) : NamedModel

@Serializable
data class PostCampaign(
    override val name: String
) : PostModel

@Serializable
data class JoinCampaign(
    val character: String
)

@Serializable(with = CampaignForeignFieldSerializer::class)
data class CampaignForeignField(
    override var url: String,
    override var value: Campaign? = null,
    override val getValues: suspend () -> List<Campaign> = {
        var paginatedResponse = HttpService.getCampaigns(null)
        val items = ArrayList<Campaign>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getCampaigns(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    },
    override val getValue: suspend () -> Campaign = {
        HttpService.getModelInstance(url)
    }

) : ForeignField<Campaign>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = CampaignForeignField::class)
object CampaignForeignFieldSerializer : KSerializer<CampaignForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: CampaignForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): CampaignForeignField {
        val url = decoder.decodeString()
        return CampaignForeignField(url = url ?: "")
    }
}
