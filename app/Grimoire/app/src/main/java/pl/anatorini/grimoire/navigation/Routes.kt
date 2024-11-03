package pl.anatorini.grimoire.navigation

import kotlinx.serialization.Serializable


enum class MasterRoutes {
    MAIN,
}

enum class Routes {
    HOME,
    SETTINGS,
    CAMPAIGNS,
    CHARACTERS,
    ARCHIVE,
    AUTH
}

enum class SettingsRoutes {
    HOME,
    SERVER,
    OTHER
}


@Serializable
data class CharacterRoute(val characterId: Int)