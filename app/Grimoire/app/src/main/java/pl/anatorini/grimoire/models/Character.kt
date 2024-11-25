package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    override var url: String = "",
    override var name: String = "",
    var player:String = "",
    var classname: ClassForeignField = ClassForeignField(""),
    var experience:Int = 0,
    var info:String? = "",
    var background:BackgroundForeignField =BackgroundForeignField(""),
    var alignment: AlignmentForeignField=AlignmentForeignField(""),
    var race:RaceForeignField = RaceForeignField(""),
    var deathSaveSuccess: Int=0,
    var deathSaveFailure: Int=0,
    var temporaryHitpoint:Int =0,
    var items:List<ItemForeignField> = emptyList(),
    var statistics:List<String> = emptyList(),
    var skills: List<String> = emptyList(),
    var equipment:List<String> = emptyList(),
    var spells: List<String> = emptyList(),

    ):Model

@Serializable
data class  PostCharacter(
    override var name: String,
    var player:String,
    var classname: ClassForeignField,
    var experience:Int,
    var info:String?,
    var background:BackgroundForeignField,
    var alignment: AlignmentForeignField,
    var race:RaceForeignField,
    var deathSaveSuccess: Int,
    var deathSaveFailure: Int,
    var temporaryHitpoint:Int,
    var items:List<ItemForeignField> ,
    var statistics:List<String> ,
    var skills: List<String> ,
    var equipment:List<String> ,
    var spells: List<String> ,
):PostModel