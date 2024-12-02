package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetail(
    val url: String,
    val player: String,
    val name: String,
    val classname: ClassResponse,
    val caster_info: String?,  // Can be null
    val experience: Int,
    val info: CharacterInfo,
    val background: Background,
    val alignment: Alignment,
    val race: Race,
    val deathSaveSuccess: Int,
    val deathSaveFailure: Int,
    val temporaryHitpoint: Int
)

@Serializable
data class ClassResponse(
    val url: String,
    val name: String,
    val spellcastingAbility: String
)

@Serializable
data class CharacterInfo(
    val age: Int,
    val height: Int,
    val weight: Int,
    val eyes: String,
    val skin: String,
    val hair: String,
    val allies_and_orgs: String,
    val appearance: String,
    val backstory: String,
    val treasure: String,
    val additionalFeaturesAndTraits: String
)
