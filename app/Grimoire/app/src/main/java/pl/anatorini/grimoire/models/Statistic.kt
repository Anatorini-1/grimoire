package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Statistic(
    override var url: String = "",
    override var name: String = ""
) : Model

@Serializable
data class PostStatistic(
    override val name: String
) : PostModel