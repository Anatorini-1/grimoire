@file:Suppress("EXTERNAL_SERIALIZER_USELESS")

package pl.anatorini.grimoire.models

interface ForeignField<T : NamedModel> {
    var value: T?;
    var url: String;
    val getValues: suspend () -> List<T>
    val getValue: suspend () -> T
}


interface UnnamedForeignField<T : Model> {
    var value: T?;
    var url: String;
    val getValues: suspend () -> List<T>
    val getValue: suspend () -> T
}
