@file:Suppress("EXTERNAL_SERIALIZER_USELESS")

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

@Serializable(with = ForeignFieldSerializer::class)
data class AlignmentForeignField(
    override var url: String,
    override var value: Alignment? = null,
    override val getValues: suspend () -> List<Alignment> = {
        var paginatedResponse = HttpService.getAlignments(null)
        val items = ArrayList<Alignment>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getAlignments(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Alignment>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = AlignmentForeignField::class)
object ForeignFieldSerializer : KSerializer<AlignmentForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: AlignmentForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): AlignmentForeignField {
        val url = decoder.decodeString()
        return AlignmentForeignField(url = url ?: "")
    }
}


@Serializable(with = StatisticForeignFieldSerializer::class)
data class StatisticForeignField(
    override var url: String,
    override var value: Statistic? = null,
    override val getValues: suspend () -> List<Statistic> = {
        var paginatedResponse = HttpService.getStatistics(null)
        val items = ArrayList<Statistic>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getStatistics(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Statistic> {
    override fun toString(): String = url
}


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = StatisticForeignField::class)
object StatisticForeignFieldSerializer : KSerializer<StatisticForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: StatisticForeignField) {
        encoder.encodeString(value.url)
    }

    override fun deserialize(decoder: Decoder): StatisticForeignField {
        val url: String? = decoder.decodeString()
        return StatisticForeignField(url = url ?: "")
    }
}
