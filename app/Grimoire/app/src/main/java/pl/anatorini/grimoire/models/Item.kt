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
data class Item(
    override var url: String = "",
    override var name: String = "",
    var weight: Float = 0f,
    var value: Float = 0f,
    var weapon: Boolean = false,
    var attackBonus: Int? = null,
    var damage: String? = null,
    var description: String? = null,
) : Model

@Serializable
data class PostItem(
    override var name: String = "",
    var weight: Float = 0f,
    var value: Float = 0f,
    var weapon: Boolean = false,
    var attackBonus: Int? = null,
    var damage: String? = null,
    var description: String? = null,
):PostModel

@Serializable(with = ItemForeignFieldSerializer::class)
data class ItemForeignField(
    override var url: String,
    override var value: Item? = null,
    override val getValues: suspend () -> List<Item> = {
        var paginatedResponse = HttpService.getItems(null)
        val items = ArrayList<Item>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getItems(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Item>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = ItemForeignField::class)
object ItemForeignFieldSerializer : KSerializer<ItemForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: ItemForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): ItemForeignField {
        val url = decoder.decodeString()
        return ItemForeignField(url = url ?: "")
    }
}
