package pl.anatorini.grimoire.state

import kotlinx.serialization.Serializable

@Serializable
data class Auth(
    val isLoggedIn: Boolean = false,
    val token: String? = null,
    val username: String? = null,
    val profilePicture: String? = null,
)