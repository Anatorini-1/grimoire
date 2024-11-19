package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val count: Int,
    val previous: String?,
    val next: String?,
    val results: ArrayList<T>
)
