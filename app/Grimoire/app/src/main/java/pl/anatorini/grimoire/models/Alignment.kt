package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Alignment(
    override var name: String = "",
    override var url: String = ""
) : Model


@Serializable
data class PostAlignment(
    override var name: String = "",
) : PostModel

