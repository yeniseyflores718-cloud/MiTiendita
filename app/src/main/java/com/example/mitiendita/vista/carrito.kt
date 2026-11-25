package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class carrito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            }
        val nav = findViewById<BottomNavigationView>(R.id.bottomNavCliente)
        nav.selectedItemId = R.id.nav_carrito
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_catalogo -> { abrir(Busqueda_de_producto::class.java); true }
                R.id.nav_carrito -> true
                R.id.nav_pedidos -> { abrir(VistaMisPedidos::class.java); true }
                R.id.nav_cuenta -> { abrir(CuentaCliente::class.java); true }

                else -> false
            }
        }
    }
    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino))
        overridePendingTransition(0, 0)
        finish()
    }
}