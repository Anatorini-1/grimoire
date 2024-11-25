package pl.anatorini.grimoire.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    var username: String,
    var token: String,
)

@Serializable
data class LoginData(
    val username:String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token:String
)

@Serializable
data class RegisterData(
    val username: String,
    val email:String,
    val password:String
)

