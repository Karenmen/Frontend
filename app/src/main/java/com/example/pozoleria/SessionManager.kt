package com.example.pozoleria

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val PREF_NAME = "POZOLERIA_SESSION"
    private val KEY_EMAIL = "email"
    private val KEY_UID = "uid"
    private val KEY_NOMBRE = "nombre"
    private val KEY_LOGGED = "isLogged"

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ✅ NO rompe tu MainActivity porque "nombre" tiene default
    fun saveSession(email: String, uid: String, nombre: String = "") {
        pref.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_UID, uid)
            .putString(KEY_NOMBRE, nombre)
            .putBoolean(KEY_LOGGED, true)
            .apply()
    }

    fun isLoggedIn(): Boolean = pref.getBoolean(KEY_LOGGED, false)

    fun logout() {
        pref.edit().clear().apply()
    }

    fun getEmail(): String? = pref.getString(KEY_EMAIL, null)
    fun getUid(): String? = pref.getString(KEY_UID, null)
    fun getNombre(): String? = pref.getString(KEY_NOMBRE, null)
}
