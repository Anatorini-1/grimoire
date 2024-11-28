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
data class Statistic(
    override var url: String = "",
    override var name: String = ""
) : NamedModel

@Serializable
data class PostStatistic(
    override val name: String
) : PostModel


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
    },
    override val getValue: suspend () -> Statistic = {

        HttpService.getModelInstance(url)
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

