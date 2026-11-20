package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_de_sesion)
        etCorreo = findViewById(R.id.editTextcorreoelectronico)
        etContrasena = findViewById(R.id.editTxtContrase)
        btnLogin = findViewById(R.id.button)

        presenter = loginPresenter(this, lifecycleScope)

        btnLogin.setOnClickListener {
            presenter.iniciarSesion(etCorreo.text.toString(), etContrasena.text.toString())
        }
        findViewById<TextView>(R.id.textView2).setOnClickListener {
            startActivity(Intent(this, crear_cuenta::class.java))
        }
        findViewById<Button>(R.id.btnVerSinIniciarSesion).setOnClickListener {
            startActivity(Intent(this, Busqueda_de_producto::class.java))
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