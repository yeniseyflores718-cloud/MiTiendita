package com.example.mitiendita.presentador

import com.example.mitiendita.interfaceApi.ApiService
import com.example.mitiendita.modelo.LoginRequest
import com.example.mitiendita.modelo.LoginResponse
import com.example.mitiendita.modelo.RetrofitCliente
import com.example.mitiendita.modelo.clsLoginModelo
import com.example.mitiendita.vista.inicio_de_sesion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response

class loginPresenter(private val view: inicio_de_sesion) {
    private val modelo = clsLoginModelo()

    fun realizarLogin(correo: String, clave: String) {

        // 1. Revisar que no estén vacíos (esto lo decide el presentador)
        if (correo.isEmpty() || clave.isEmpty()) {
            view.mostrarError("Por favor, ingresa tu correo y contraseña")
            return
        }

        // 2. Lanzar la tarea de internet sin congelar la pantalla
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 3. Ir a internet (en segundo plano) y pedirle al modelo que haga login
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.autenticarUsuario(correo, clave)
                }

                // 4. Ver qué respondió la API
                if (respuesta.exito && respuesta.usuario != null) {
                    // Guardar el token en el teléfono
                    RetrofitCliente.tokenManager.guardarSesion(respuesta.token, respuesta.usuario)

                    val nombre = respuesta.usuario.nombreUsuario ?: respuesta.usuario.usuario
                    view.mostrarBienvenida(nombre)

                    // Decidir a qué pantalla va, según el rol que mandó la API
                    when (respuesta.usuario.rol.lowercase()) {
                        "administrador" -> view.irAAdministrador()
                        "cajero", "vendedor", "empleado" -> view.irAEmpleado()
                        else -> view.irACliente()          // "cliente" y "usuario"
                    }
                } else {
                    view.mostrarError(respuesta.mensaje)
                }

            } catch (e: HttpException) {
                // La API respondió con error (401 contraseña mala, 403 cuenta inactiva...)
                val mensaje = try {
                    val cuerpo = e.response()?.errorBody()?.string() ?: ""
                    Json { ignoreUnknownKeys = true }
                        .decodeFromString<LoginResponse>(cuerpo).mensaje
                } catch (ex: Exception) {
                    "Error del servidor (${e.code()})"
                }
                view.mostrarError(mensaje)

            } catch (e: Exception) {
                // No hubo internet, el servidor está apagado, URL mal escrita, etc.
                view.mostrarError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

}