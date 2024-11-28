package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable


@Serializable
data class UnnamedPaginatedResponse<T : Model>(
    var count: Int,
    var previous: String?,
    var next: String?,
    var results: ArrayList<T>
)

@Serializable
data class PaginatedResponse<T : NamedModel>(
    var count: Int,
    var previous: String?,
    var next: String?,
    var results: ArrayList<T>
)
