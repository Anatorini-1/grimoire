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
data class CharacterClass(
    override var name: String = "",
    override var url: String = "",
    var spellcastingAbility: StatisticForeignField = StatisticForeignField("")
) : Model


@Serializable
data class PostCharacterClass(
    override var name: String,
    var spellcastingAbility: StatisticForeignField,
) : PostModel



@Serializable(with = ClassForeignFieldSerializer::class)
data class ClassForeignField(
    override var url: String,
    override var value: CharacterClass? = null,
    override val getValues: suspend () -> List<CharacterClass> = {
        var paginatedResponse = HttpService.getClasses(null)
        val items = ArrayList<CharacterClass>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getClasses(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<CharacterClass>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = ClassForeignField::class)
object ClassForeignFieldSerializer : KSerializer<ClassForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: ClassForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): ClassForeignField {
        val url = decoder.decodeString()
        return ClassForeignField(url = url ?: "")
    }
}
