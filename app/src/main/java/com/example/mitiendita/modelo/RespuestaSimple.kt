package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaSimple(val exito: Boolean, val mensaje: String)