package com.example.mitiendita.presentador

import com.example.mitiendita.vista.baseVista

interface basePresentador<V : baseVista> {
    fun asociarVista(vista: V)
    fun desasociarVista()
}