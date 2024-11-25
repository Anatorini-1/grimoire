package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable


@Serializable
data class Item(
    override var url: String = "",
    override var name: String = "",
    var weight: Float = 0f,
    var value: Float = 0f,
    var weapon: Boolean = false,
    var attackBonus: Int? = null,
    var damage: String? = null,
    var description: String? = null,
) : Model

@Serializable
data class PostItem(
    override var name: String = "",
    var weight: Float = 0f,
    var value: Float = 0f,
    var weapon: Boolean = false,
    var attackBonus: Int? = null,
    var damage: String? = null,
    var description: String? = null,
):PostModel