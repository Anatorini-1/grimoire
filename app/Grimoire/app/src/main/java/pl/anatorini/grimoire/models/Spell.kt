package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    override val url: String,
    override val name: String,
    val description: String,
    val level: Int,
    val ritual: Boolean,
    val range: String,
    val duration: String,
    val school: String,
) : Model