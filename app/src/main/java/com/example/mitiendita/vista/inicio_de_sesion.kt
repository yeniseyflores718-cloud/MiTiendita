package com.example.mitiendita.vista

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.example.mitiendita.presentador.loginPresenter

class inicio_de_sesion : AppCompatActivity() {

    private lateinit var presenter: loginPresenter
    private lateinit var txtCorreo: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtCrearCuenta: TextView
    private lateinit var txtOlvidaste: TextView

    private var contrasenaVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_de_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Enlazar los componentes del XML
        txtCorreo = findViewById(R.id.editTextcorreoelectronico)
        txtContrasena = findViewById(R.id.editTxtContrase)
        btnLogin = findViewById(R.id.button)

        // 2. Crear el presentador (le pasamos "this", o sea esta pantalla)
        presenter = loginPresenter(this)

        // 3. Qué pasa al tocar el botón
        btnLogin.setOnClickListener(this::ejecutarInicioSesion)
        txtCrearCuenta = findViewById(R.id.textView2)
        txtOlvidaste = findViewById(R.id.textView15)


        txtCrearCuenta.setOnClickListener {
            startActivity(Intent(this, crear_cuenta::class.java))
        }

        txtOlvidaste.setOnClickListener {
            startActivity(Intent(this, Recuperar_Contrasena::class.java))
        }


        configurarOjito()
    }

    fun ejecutarInicioSesion(v: View) {
        val usuario = txtCorreo.text.toString().trim()
        val clave = txtContrasena.text.toString().trim()
        presenter.realizarLogin(usuario, clave)   // la vista NO piensa: se lo pasa al presentador
    }

    // El presentador llama a esta función cuando todo salió bien
    fun mostrarBienvenida(nombre: String) {
        Toast.makeText(this, "¡Bienvenido/a $nombre!", Toast.LENGTH_SHORT).show()
    }

    fun irAAdministrador() {
        abrirLimpiando(administrador::class.java)
    }

    fun irAEmpleado() {
        abrirLimpiando(MenuEmpleado::class.java)
    }

    fun irACliente() {
        finish()   // el cliente regresa al catálogo, de donde abrió el login
    }


    private fun abrirLimpiando(destino: Class<*>) {
        val intent = Intent(this, destino)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    // El presentador llama a esta función cuando algo salió mal
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun configurarOjito() {
        txtContrasena.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val icono = txtContrasena.compoundDrawablesRelative[2]   // el drawableEnd
                if (icono != null &&
                    event.x >= v.width - v.paddingEnd - icono.bounds.width() - 60) {
                    alternarContrasena()
                    v.performClick()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun alternarContrasena() {
        contrasenaVisible = !contrasenaVisible
        val fuente = txtContrasena.typeface   // se guarda para que la letra no cambie

        txtContrasena.inputType = if (contrasenaVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        txtContrasena.typeface = fuente
        txtContrasena.setSelection(txtContrasena.text.length)   // cursor al final

        val icono = if (contrasenaVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
        txtContrasena.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, icono, 0)
    }
}