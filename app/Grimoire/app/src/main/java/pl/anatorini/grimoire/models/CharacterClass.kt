package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterClass(
    override var name: String = "",
    override var url: String = "",
    var spellcastingAbility: StatisticForeignField = StatisticForeignField("")
) : Model


@Serializable
data class PostCharacterClass(
    override var name: String,
    var spellcastingAbility: StatisticForeignField,
) : PostModel


