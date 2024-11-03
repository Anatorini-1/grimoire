package pl.anatorini.grimoire.services

import android.util.Log
import pl.anatorini.grimoire.state.Auth

class HttpService(private val auth: Auth) {
    fun login() {
        Log.println(Log.INFO, "", "$auth")
    }
}