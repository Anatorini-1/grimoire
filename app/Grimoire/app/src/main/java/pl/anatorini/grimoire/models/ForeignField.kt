@file:Suppress("EXTERNAL_SERIALIZER_USELESS")

package pl.anatorini.grimoire.models

interface ForeignField<T : Model> {
    var value: T?;
    var url: String;
    val getValues: suspend () -> List<T>
}