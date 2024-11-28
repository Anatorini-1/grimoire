package pl.anatorini.grimoire.models

interface Model {
    val url: String
}

interface NamedModel : Model {
    val name: String
    override val url: String
}

interface PostModel {
    val name: String
}