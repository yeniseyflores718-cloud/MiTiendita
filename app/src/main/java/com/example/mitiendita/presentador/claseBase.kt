package com.example.mitiendita.presentador

import com.example.mitiendita.vista.baseVista

abstract class claseBase<V : baseVista> : basePresentador<V> {
    protected var vista: V? = null

    override fun asociarVista(vista: V) {
        this.vista = vista
    }

    override fun desasociarVista() {
        vista = null
    }
}