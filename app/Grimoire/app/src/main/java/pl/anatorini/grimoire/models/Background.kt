package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Background(
    override var name: String = "",
    override var url: String = ""
) : Model

@Serializable
data class PostBackground(
    override var name: String = "",
): PostModel
