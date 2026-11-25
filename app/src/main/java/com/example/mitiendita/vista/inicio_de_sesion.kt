package com.example.mitiendita.vista

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mitiendita.R
import com.example.mitiendita.modelo.UsuarioData
import com.example.mitiendita.presentador.loginPresenter
import kotlin.jvm.java

class inicio_de_sesion : AppCompatActivity(), baseVista.View {
    private lateinit var presenter: loginPresenter
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnLogin: Button
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_de_sesion)
        etCorreo = findViewById(R.id.editTextcorreoelectronico)
        etContrasena = findViewById(R.id.editTxtContrase)
        btnLogin = findViewById(R.id.button)

        presenter = loginPresenter(this, lifecycleScope)

        var contraVisible = false
        etContrasena.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val icono = etContrasena.compoundDrawables[2]
                if (icono != null &&
                    event.rawX >= etContrasena.right - icono.bounds.width() - etContrasena.paddingEnd - 20) {

                    contraVisible = !contraVisible
                    etContrasena.inputType = if (contraVisible)
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                    etContrasena.typeface = Typeface.DEFAULT
                    etContrasena.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        if (contraVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off,
                        0
                    )
                    etContrasena.setSelection(etContrasena.text.length)
                    v.performClick()
                    return@setOnTouchListener true
                }
            }
            false
        }

        btnLogin.setOnClickListener {
            presenter.iniciarSesion(etCorreo.text.toString(), etContrasena.text.toString())
        }
        findViewById<TextView>(R.id.textView2).setOnClickListener {
            startActivity(Intent(this, crear_cuenta::class.java))
        }
        findViewById<Button>(R.id.btnVerSinIniciarSesion).setOnClickListener {
            startActivity(Intent(this, Busqueda_de_producto::class.java))
        }
        findViewById<TextView>(R.id.textView15).setOnClickListener {
            startActivity(Intent(this, Recuperar_Contrasena::class.java))
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun mostrarCargando() {
        btnLogin.isEnabled = false
        btnLogin.text = "Entrando..."
    }

    override fun ocultarCargando() {
        btnLogin.isEnabled = true
        btnLogin.text = "Iniciar sesión"
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun loginExitoso(token: String, usuario: UsuarioData) {
        // guardamos la sesión para usarla en las demás pantallas
        getSharedPreferences("sesion", MODE_PRIVATE).edit()
            .putString("token", token)
            .putInt("id_usuario", usuario.idUsuario)
            .putString("nombre", usuario.nombreUsuario)
            .putString("correo", usuario.correo)
            .putString("telefono", usuario.telefono)
            .putString("rol", usuario.rol)
            .apply()

        val destino = when (usuario.rol) {
            "administrador", "vendedor" -> administrador::class.java
            else -> Busqueda_de_producto::class.java
        }
        startActivity(Intent(this, destino))
        finish()
    }
}