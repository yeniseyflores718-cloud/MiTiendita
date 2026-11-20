package com.example.mitiendita.presentador

import com.example.mitiendita.interfaceApi.ApiService
import com.example.mitiendita.modelo.LoginRequest
import com.example.mitiendita.modelo.LoginResponse
import com.example.mitiendita.modelo.RetrofitCliente
import com.example.mitiendita.vista.baseVista
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Response

class loginPresenter(
    private val view: baseVista.View,
    private val scope: CoroutineScope,
    private val api: ApiService = RetrofitCliente.apiService
) {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    fun iniciarSesion(usuario: String, contrasena: String) {
        val u = usuario.trim()
        if (u.isEmpty() || contrasena.isEmpty()) {
            view.mostrarError("Escribe tu correo y contraseña")
            return
        }

        view.mostrarCargando()
        scope.launch {
            try {
                val response = api.login(LoginRequest(u, contrasena))
                val body = response.body()

                if (response.isSuccessful && body != null && body.exito &&
                    body.token != null && body.usuario != null
                ) {
                    view.loginExitoso(body.token, body.usuario)
                } else {
                    view.mostrarError(extraerMensaje(response))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                view.mostrarError("No se pudo conectar con el servidor")
            } finally {
                view.ocultarCargando()
            }
        }
    }
    private fun extraerMensaje(response: Response<LoginResponse>): String {
        if (response.isSuccessful) {
            return response.body()?.mensaje?.takeIf { it.isNotBlank() }
                ?: "No se pudo iniciar sesión"
        }
        val cuerpo = response.errorBody()?.string()
        if (!cuerpo.isNullOrBlank()) {
            try {
                val msg = json.decodeFromString<LoginResponse>(cuerpo).mensaje
                if (msg.isNotBlank()) return msg
            } catch (_: Exception) { }
        }
        return "Error al iniciar sesión (${response.code()})"
    }
}