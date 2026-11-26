package com.example.mitiendita

import android.app.Application
import com.example.mitiendita.modelo.RetrofitCliente
import com.example.mitiendita.modelo.TokenManager

class MiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitCliente.tokenManager = TokenManager(this)
    }
}