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
data class Spell(
    override var url: String = "",
    override var name: String = "",
    var description: String = "",
    var level: Int = 0,
    var ritual: Boolean = false,
    var range: String = "",
    var duration: String = "",
    var school: String = "",
) : Model

@Serializable
data class PostSpell(
    override var name: String = "",
    var description: String = "",
    var level: Int = 0,
    var ritual: Boolean = false,
    var range: String = "",
    var duration: String = "",
    var school: String = "",
):PostModel




@Serializable(with = SpellForeignFieldSerializer::class)
data class SpellForeignField(
    override var url: String,
    override var value: Spell? = null,
    override val getValues: suspend () -> List<Spell> = {
        var paginatedResponse = HttpService.getSpells(null)
        val items = ArrayList<Spell>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getSpells(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Spell>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = SpellForeignField::class)
object SpellForeignFieldSerializer : KSerializer<SpellForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: SpellForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): SpellForeignField {
        val url = decoder.decodeString()
        return SpellForeignField(url = url ?: "")
    }
}
