package com.example.mitiendita.vista

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.example.mitiendita.presentador.RegistroPresenter

class crear_cuenta : AppCompatActivity(), RegistroContrato.Vista  {

    private lateinit var presentador: RegistroContrato.Presentador

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etContrasena: EditText
    private lateinit var etConfirmacion: EditText
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnAtras: ImageView
    private lateinit var txtIniciarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombre = findViewById(R.id.editTxtNombre)
        etCorreo = findViewById(R.id.editTxtcorreoelectronico)
        etTelefono = findViewById(R.id.editTxtNumeroTelefonico)
        etContrasena = findViewById(R.id.editTxtContraseña)
        etConfirmacion = findViewById(R.id.editTxtContraseña2)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnAtras = findViewById(R.id.imageView2)
        txtIniciarSesion = findViewById(R.id.txtIniciarSesion)

        presentador = RegistroPresenter(this)

        btnCrearCuenta.setOnClickListener {
            limpiarErrores()
            presentador.registrar(
                etNombre.text.toString(),
                etCorreo.text.toString(),
                etTelefono.text.toString(),
                etContrasena.text.toString(),
                etConfirmacion.text.toString()
            )
        }

        btnAtras.setOnClickListener { finish() }
        txtIniciarSesion.setOnClickListener { finish() }
    }

    private fun limpiarErrores() {
        etNombre.error = null
        etCorreo.error = null
        etTelefono.error = null
        etContrasena.error = null
        etConfirmacion.error = null
    }

    override fun mostrarCargando(mostrar: Boolean) {
        btnCrearCuenta.isEnabled = !mostrar
        btnCrearCuenta.text = if (mostrar) "Creando cuenta..." else "Crear cuenta"
    }

    override fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun errorNombre(mensaje: String) { etNombre.error = mensaje }
    override fun errorCorreo(mensaje: String) { etCorreo.error = mensaje }
    override fun errorTelefono(mensaje: String) { etTelefono.error = mensaje }
    override fun errorContrasena(mensaje: String) { etContrasena.error = mensaje }
    override fun errorConfirmacion(mensaje: String) { etConfirmacion.error = mensaje }

    override fun registroExitoso() {
        finish()
    }

    override fun onDestroy() {
        presentador.destruir()
        super.onDestroy()
    }
}