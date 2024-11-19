package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable


@Serializable
data class Item(
    override val url: String,
    override val name: String,
    val weight: Float,
    val value: Float,
    val weapon: Boolean = false,
    val attackBonus: Int? = null,
    val damage: String? = null,
    val description: String? = null,
) : Model
