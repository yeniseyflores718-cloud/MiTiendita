package com.example.mitiendita.modelo

import android.content.Context

data class PerfilLocal(val nombre: String, val correo: String, val telefono: String?)

class SesionLocal(context: Context) {
    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    fun perfil(): PerfilLocal? {
        if (prefs.getString("token", null) == null) return null
        return PerfilLocal(
            nombre = prefs.getString("nombre", "") ?: "",
            correo = prefs.getString("correo", "") ?: "",
            telefono = prefs.getString("telefono", null)
        )
    }

    fun cerrar() = prefs.edit().clear().apply()
}
