package com.example.mitiendita.vista

interface RegistroContrato {
    interface Vista {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarMensaje(mensaje: String)
        fun errorNombre(mensaje: String)
        fun errorCorreo(mensaje: String)
        fun errorTelefono(mensaje: String)
        fun errorContrasena(mensaje: String)
        fun errorConfirmacion(mensaje: String)
        fun registroExitoso()
    }

    interface Presentador {
        fun registrar(
            nombre: String,
            correo: String,
            telefono: String,
            contrasena: String,
            confirmacion: String
        )
        fun destruir()
    }
}