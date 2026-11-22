package com.example.mitiendita.modelo

sealed class ResultadoRegistro {
    data class Exito(val mensaje: String) : ResultadoRegistro()
    data class Fallo(val mensaje: String) : ResultadoRegistro()
}

class RegistroModelo {

    suspend fun registrar(request: RegistroRequest): ResultadoRegistro {
        return try {
            val response = RetrofitCliente.apiService.registrar(request)
            val body = response.body()

            when {
                response.isSuccessful && body != null && body.exito ->
                    ResultadoRegistro.Exito(body.mensaje)
                response.isSuccessful && body != null ->
                    ResultadoRegistro.Fallo(body.mensaje)
                else ->
                    ResultadoRegistro.Fallo("Error del servidor (${response.code()})")
            }
        } catch (e: Exception) {
            ResultadoRegistro.Fallo("Sin conexión: ${e.message}")
        }
    }
}