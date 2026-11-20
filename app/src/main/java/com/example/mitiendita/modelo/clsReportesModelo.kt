package com.example.mitiendita.modelo

class clsReportesModelo {
    suspend fun obtenerReportes(idUsuario: Int, periodo: String): ReportesResponse {
        return RetrofitCliente.apiService.obtenerReportes(idUsuario, periodo)
    }

    suspend fun obtenerReportesSemana(idUsuario: Int): ReportesResponse {
        return RetrofitCliente.apiService.obtenerReportes(idUsuario, "semana")
    }

    suspend fun obtenerReportesMes(idUsuario: Int): ReportesResponse {
        return RetrofitCliente.apiService.obtenerReportes(idUsuario, "mes")
    }
}