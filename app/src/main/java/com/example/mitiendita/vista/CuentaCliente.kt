package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.example.mitiendita.modelo.SesionLocal
import com.example.mitiendita.presentador.CuentaView
import com.example.mitiendita.presentador.cuentaPresentador
import com.google.android.material.bottomnavigation.BottomNavigationView

class CuentaCliente : AppCompatActivity(), CuentaView {
    private lateinit var presenter: cuentaPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_cliente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        presenter = cuentaPresentador(this, SesionLocal(this))
        presenter.cargarPerfil()

        findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            presenter.cerrarSesion()
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNavCliente)
        nav.selectedItemId = R.id.nav_cuenta
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_catalogo -> { abrir(Busqueda_de_producto::class.java); true }
                R.id.nav_carrito -> {abrir(carrito::class.java);true }
                R.id.nav_pedidos -> { abrir(VistaMisPedidos::class.java); true }
                R.id.nav_cuenta -> true
                else -> false
            }
        }
    }

    override fun mostrarPerfil(nombre: String, correo: String, telefono: String, iniciales: String) {
        findViewById<TextView>(R.id.tvNombreHeader).text = nombre
        findViewById<TextView>(R.id.tvNombre).text = nombre
        findViewById<TextView>(R.id.tvCorreo).text = correo
        findViewById<TextView>(R.id.tvTelefono).text = telefono
        findViewById<TextView>(R.id.tvIniciales).text = iniciales
    }

    override fun irALogin() {
        startActivity(Intent(this, inicio_de_sesion::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino))
        overridePendingTransition(0, 0)
        finish()
    }
}