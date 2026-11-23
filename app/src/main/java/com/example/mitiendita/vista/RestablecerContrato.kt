package com.example.mitiendita.vista

interface RestablecerContrato {
    interface Vista {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarMensaje(mensaje: String)
        fun errorCodigo(mensaje: String)
        fun errorContrasena(mensaje: String)
        fun errorConfirmacion(mensaje: String)
        fun contrasenaActualizada()
    }

    interface Presentador {
        fun restablecer(correo: String, codigo: String, nueva: String, confirmacion: String)
        fun destruir()
    }
}