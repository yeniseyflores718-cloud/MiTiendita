package com.example.mitiendita.modelo

class clsLoginModelo {
    suspend fun autenticarUsuario(correo: String, clave: String): LoginResponse {
        val request = LoginRequest(correo = correo, contrasena = clave)
        return RetrofitCliente.apiService.iniciarSesion(request)
    }
}