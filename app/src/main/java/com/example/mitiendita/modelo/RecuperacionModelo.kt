package com.example.mitiendita.modelo

import retrofit2.Response

class RecuperacionModelo {

    suspend fun solicitarCodigo(correo: String): Pair<Boolean, String> =
        ejecutar { RetrofitCliente.apiService.solicitarRecuperacion(SolicitarRecuperacionRequest(correo)) }

    suspend fun restablecer(correo: String, codigo: String, nueva: String): Pair<Boolean, String> =
        ejecutar { RetrofitCliente.apiService.restablecerContrasena(RestablecerRequest(correo, codigo, nueva)) }

    private suspend fun ejecutar(llamada: suspend () -> Response<RespuestaSimple>): Pair<Boolean, String> {
        return try {
            val response = llamada()
            val body = response.body()
            if (response.isSuccessful && body != null) Pair(body.exito, body.mensaje)
            else Pair(false, "Error del servidor (${response.code()})")
        } catch (e: Exception) {
            Pair(false, "Sin conexión: ${e.message}")
        }
    }
}