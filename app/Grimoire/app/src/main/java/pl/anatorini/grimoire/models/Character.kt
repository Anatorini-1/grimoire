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
data class Character(
    override var url: String = "",
    override var name: String = "",
    var player: String = "",
    var classname: ClassForeignField = ClassForeignField(""),
    var experience: Int = 0,
    var info: String? = "",
    var background: BackgroundForeignField = BackgroundForeignField(""),
    var alignment: AlignmentForeignField = AlignmentForeignField(""),
    var race: CharacterForeignField = CharacterForeignField(""),
    var deathSaveSuccess: Int = 0,
    var deathSaveFailure: Int = 0,
    var temporaryHitpoint: Int = 0,
    var items: List<ItemForeignField> = emptyList(),
    var statistics: List<String> = emptyList(),
    var skills: List<String> = emptyList(),
    var equipment: List<String> = emptyList(),
    var spells: List<String> = emptyList(),

    ) : NamedModel

@Serializable
data class PostCharacter(
    override var name: String,
    var player: String,
    var classname: ClassForeignField,
    var experience: Int,
    var info: String?,
    var background: BackgroundForeignField,
    var alignment: AlignmentForeignField,
    var race: CharacterForeignField,
    var deathSaveSuccess: Int,
    var deathSaveFailure: Int,
    var temporaryHitpoint: Int,
    var items: List<ItemForeignField>,
    var statistics: List<String>,
    var skills: List<String>,
    var equipment: List<String>,
    var spells: List<String>,
) : PostModel


@Serializable(with = CharacterForeignFieldSerializer::class)
data class CharacterForeignField(
    override var url: String,
    override var value: Character? = null,
    override val getValues: suspend () -> List<Character> = {
        var paginatedResponse = HttpService.getCharacters(null)
        val items = ArrayList<Character>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getCharacters(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    },
    override val getValue: suspend () -> Character = {

        HttpService.getModelInstance(url)
    }
) : ForeignField<Character>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = CharacterForeignField::class)
object CharacterForeignFieldSerializer : KSerializer<CharacterForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: CharacterForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): CharacterForeignField {
        val url = decoder.decodeString()
        return CharacterForeignField(url = url ?: "")
    }
}
