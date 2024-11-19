package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterClass(
    override val name: String,
    override val url: String,
    val spellcastingAbility: String? = null
) : Model



