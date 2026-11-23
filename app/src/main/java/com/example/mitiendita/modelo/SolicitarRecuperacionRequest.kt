package com.example.mitiendita.modelo

import kotlinx.serialization.Serializable

@Serializable
data class SolicitarRecuperacionRequest(val correo: String)