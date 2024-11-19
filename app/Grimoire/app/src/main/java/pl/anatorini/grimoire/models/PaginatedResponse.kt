package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    var count: Int,
    var previous: String?,
    var next: String?,
    var results: ArrayList<T>
)
