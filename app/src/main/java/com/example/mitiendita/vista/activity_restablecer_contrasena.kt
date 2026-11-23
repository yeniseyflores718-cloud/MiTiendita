package com.example.mitiendita.vista

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.example.mitiendita.presentador.RestablecerPresenter
import com.google.android.material.button.MaterialButton

class activity_restablecer_contrasena : AppCompatActivity(), RestablecerContrato.Vista  {
    private lateinit var presentador: RestablecerContrato.Presentador
    private lateinit var etCodigo: EditText
    private lateinit var etNueva: EditText
    private lateinit var etConfirmacion: EditText
    private lateinit var btnRestablecer: MaterialButton
    private var correo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restablecer_contrasena)

        correo = intent.getStringExtra("correo") ?: ""

        etCodigo = findViewById(R.id.etCodigo)
        etNueva = findViewById(R.id.etNuevaContrasena)
        etConfirmacion = findViewById(R.id.etConfirmacion)
        btnRestablecer = findViewById(R.id.btnRestablecer)
        findViewById<TextView>(R.id.txtInfoCorreo).text = "Ingresa el código que enviamos a $correo."

        presentador = RestablecerPresenter(this)

        btnRestablecer.setOnClickListener {
            etCodigo.error = null
            etNueva.error = null
            etConfirmacion.error = null
            presentador.restablecer(
                correo,
                etCodigo.text.toString(),
                etNueva.text.toString(),
                etConfirmacion.text.toString()
            )
        }
        findViewById<android.view.View>(R.id.btnRegresar).setOnClickListener { finish() }
    }

    override fun mostrarCargando(mostrar: Boolean) {
        btnRestablecer.isEnabled = !mostrar
        btnRestablecer.text = if (mostrar) "Guardando..." else "Cambiar contraseña"
    }

    override fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun errorCodigo(mensaje: String) { etCodigo.error = mensaje }
    override fun errorContrasena(mensaje: String) { etNueva.error = mensaje }
    override fun errorConfirmacion(mensaje: String) { etConfirmacion.error = mensaje }

    override fun contrasenaActualizada() {
        finish() // regresa a la pantalla de correo
        finish()
    }

    override fun onDestroy() {
        presentador.destruir()
        super.onDestroy()
    }
}