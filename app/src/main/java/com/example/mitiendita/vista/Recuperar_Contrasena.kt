package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.example.mitiendita.presentador.RecuperarPresenter
import com.google.android.material.button.MaterialButton
import kotlin.jvm.java

class Recuperar_Contrasena : AppCompatActivity(), RecuperarContrato.Vista {
    private lateinit var presentador: RecuperarContrato.Presentador
    private lateinit var etCorreo: EditText
    private lateinit var btnEnviar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        etCorreo = findViewById(R.id.etCorreo)
        btnEnviar = findViewById(R.id.btnEnviarEnlace)

        presentador = RecuperarPresenter(this)

        btnEnviar.setOnClickListener {
            etCorreo.error = null
            presentador.solicitarCodigo(etCorreo.text.toString())
        }
        findViewById<android.view.View>(R.id.btnRegresar).setOnClickListener { finish() }
        findViewById<TextView>(R.id.txtIniciarSesion).setOnClickListener { finish() }
    }

    override fun mostrarCargando(mostrar: Boolean) {
        btnEnviar.isEnabled = !mostrar
        btnEnviar.text = if (mostrar) "Enviando..." else "Enviar código"
    }

    override fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun errorCorreo(mensaje: String) { etCorreo.error = mensaje }

    override fun codigoEnviado(correo: String) {
        val intent = Intent(this, activity_restablecer_contrasena::class.java)
        intent.putExtra("correo", correo)
        startActivity(intent)
    }

    override fun onDestroy() {
        presentador.destruir()
        super.onDestroy()
    }

}