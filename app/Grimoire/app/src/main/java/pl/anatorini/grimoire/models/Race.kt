package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Race(
    override var name: String = "",
    override var url: String = ""
) : Model


@Serializable
data class PostRace(
    override val name: String
) : PostModel