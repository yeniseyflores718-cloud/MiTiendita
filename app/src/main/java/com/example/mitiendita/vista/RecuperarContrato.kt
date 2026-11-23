package com.example.mitiendita.vista

interface RecuperarContrato {
    interface Vista {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarMensaje(mensaje: String)
        fun errorCorreo(mensaje: String)
        fun codigoEnviado(correo: String)
    }

    interface Presentador {
        fun solicitarCodigo(correo: String)
        fun destruir()
    }
}