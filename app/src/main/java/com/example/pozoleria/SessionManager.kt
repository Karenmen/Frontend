package com.example.pozoleria

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val PREF_NAME = "POZOLERIA_SESSION"
    private val KEY_EMAIL = "email"
    private val KEY_UID = "uid"
    private val KEY_LOGGED = "isLogged"

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // 🔥 Guardar sesión después de login exitoso
    fun saveSession(email: String, uid: String) {
        pref.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_UID, uid)
            .putBoolean(KEY_LOGGED, true)
            .apply()
    }

    // 🔥 Ver si el usuario ya está logeado
    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_LOGGED, false)
    }

    // 🔥 Cerrar sesión por completo
    fun logout() {
        pref.edit().clear().apply()
    }


    fun getEmail(): String? = pref.getString(KEY_EMAIL, null)
    fun getUid(): String? = pref.getString(KEY_UID, null)
}
