package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Alignment(
    override val name: String,
    override val url: String
) : Model
