package com.example.mitiendita.vista

interface baseVista {
    fun mostrarCargando()
    fun ocultarCargando()
    fun mostrarError(mensaje: String)
}