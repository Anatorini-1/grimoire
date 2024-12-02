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
data class CampaignPlayer(
    override var url: String,
    var campaign: CampaignForeignField = CampaignForeignField(""),
    var player: Player? = null,
    var accepted: Boolean = false,
    var character: CharacterForeignField? = CharacterForeignField(""),
) : Model


@Serializable
data class CampaignPlayerInvite(
    val player: String,
)

@Serializable(with = CampaignPlayerForeignFieldSerializer::class)
data class CampaignPlayerForeignField(
    override var url: String,
    override var value: CampaignPlayer? = null,
    override val getValue: suspend () -> CampaignPlayer = {
        HttpService.getModelInstance(url)
    },
    override val getValues: suspend () -> List<CampaignPlayer> = {
        if (value == null) {
            getValue()
        }
        var paginatedResponse =
            HttpService.getCampaignPlayers(value?.campaign?.url, null)
        val items = ArrayList<CampaignPlayer>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getCampaignPlayers(
                value?.campaign?.url,
                paginatedResponse.next
            )
            items.addAll(paginatedResponse.results)
        }
        items
    },
) : UnnamedForeignField<CampaignPlayer> {
    override fun toString(): String = url
}


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = CampaignPlayerForeignField::class)
object CampaignPlayerForeignFieldSerializer : KSerializer<CampaignPlayerForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: CampaignPlayerForeignField) {
        encoder.encodeString(value.url)
    }

    override fun deserialize(decoder: Decoder): CampaignPlayerForeignField {
        val url: String? = decoder.decodeString()
        return CampaignPlayerForeignField(url = url ?: "")
    }
}

