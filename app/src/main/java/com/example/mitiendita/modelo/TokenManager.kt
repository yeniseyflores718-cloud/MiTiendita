package com.example.mitiendita.modelo

import android.content.Context

class TokenManager(context: Context) {

    // SharedPreferences = una libretita privada de la app para datos chiquitos
    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    fun guardarSesion(token: String, usuario: UsuarioData) {
        prefs.edit()
            .putString("token", token)
            .putInt("id_usuario", usuario.idUsuario)
            .putString("nombre", usuario.nombreUsuario ?: usuario.usuario)
            .putString("rol", usuario.rol)
            .apply()
    }

    fun obtenerToken(): String? = prefs.getString("token", null)   // null = no hay sesión

    fun obtenerNombre(): String = prefs.getString("nombre", "") ?: ""
    fun obtenerRol(): String = prefs.getString("rol", "") ?: ""

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}