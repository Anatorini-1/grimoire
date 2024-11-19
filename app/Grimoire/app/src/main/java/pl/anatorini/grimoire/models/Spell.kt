package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable


@Serializable
data class Spell(
    override var url: String = "",
    override var name: String = "",
    var description: String = "",
    var level: Int = 0,
    var ritual: Boolean = false,
    var range: String = "",
    var duration: String = "",
    var school: String = "",
) : Model