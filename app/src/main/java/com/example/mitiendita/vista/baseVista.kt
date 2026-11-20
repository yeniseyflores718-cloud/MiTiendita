package com.example.mitiendita.vista

import com.example.mitiendita.modelo.UsuarioData

interface baseVista {
    interface View {
        fun mostrarCargando()
        fun ocultarCargando()
        fun mostrarError(mensaje: String)
        fun loginExitoso(token: String, usuario: UsuarioData)
    }
}