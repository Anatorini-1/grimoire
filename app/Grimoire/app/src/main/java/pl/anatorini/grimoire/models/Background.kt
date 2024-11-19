package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Background(
    override val name: String,
    override val url: String
) : Model
