package com.example.mitiendita.modelo

class clsInicioAdminModelo {
    suspend fun obtenerInicioAdmin(idUsuario: Int): InicioAdminResponse {
        return RetrofitCliente.apiService.obtenerInicioAdmin(idUsuario)
    }
}