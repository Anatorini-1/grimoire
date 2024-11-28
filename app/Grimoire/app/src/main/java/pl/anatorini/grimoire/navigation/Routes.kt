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

    ARCHIVE_CLASSES,
    ARCHIVE_SPELLS,
    ARCHIVE_RACES,
    ARCHIVE_ALIGNMENTS,
    ARCHIVE_BACKGROUNDS,
    ARCHIVE_ITEMS,

    AUTH
}

enum class SettingsRoutes {
    HOME,
    SERVER,
    OTHER
}

@Serializable
data class SessionRoute(val url: String)


@Serializable
data class CharacterRoute(val characterId: Int)

