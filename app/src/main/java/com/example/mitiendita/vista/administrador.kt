package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import com.example.mitiendita.modelo.InicioAdminResponse
import com.example.mitiendita.modelo.SesionLocal
import com.example.mitiendita.presentador.clsInicioAdminPresentador
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class administrador : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvRol: MaterialButton
    private lateinit var btnSalir: ImageView
    private lateinit var tvVentasHoy: TextView
    private lateinit var tvPedidos: TextView
    private lateinit var tvStockBajo: TextView
    private lateinit var cardProductos: MaterialCardView
    private lateinit var cardReportes: MaterialCardView
    private lateinit var bottom_nav: BottomNavigationView

    private lateinit var presentadorInicioAdmin: clsInicioAdminPresentador

    private var idUsuario: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_administrador)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvRol = findViewById(R.id.tvRol)
        btnSalir = findViewById(R.id.btnSalir)
        tvVentasHoy = findViewById(R.id.tvVentasHoy)
        tvPedidos = findViewById(R.id.tvPedidos)
        tvStockBajo = findViewById(R.id.tvStockBajo)
        cardProductos = findViewById(R.id.cardProductos)
        cardReportes = findViewById(R.id.cardReportes)
        bottom_nav = findViewById(R.id.bottom_nav)

        val sesion = SesionLocal(this)
        presentadorInicioAdmin = clsInicioAdminPresentador(vista = this, sesion = sesion)

        cardProductos.setOnClickListener(this::ejecutarAbrirProductos)
        cardReportes.setOnClickListener(this::ejecutarAbrirReportes)
        btnSalir.setOnClickListener(this::ejecutarSalir)
        bottom_nav.setOnItemSelectedListener(this::ejecutarNavegacionInferior)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ejecutarCargarDatosUsuario()
        configurarBottomNav()
        presentadorInicioAdmin.cargarInicioAdmin(idUsuario)
    }

    private fun ejecutarCargarDatosUsuario() {
        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))
        val nombreUsuario = intent.getStringExtra("nombre_usuario") ?: "Administrador"
        val rolUsuario = intent.getStringExtra("rol") ?: "administrador"

        tvNombreUsuario.text = nombreUsuario
        tvRol.text = rolUsuario.replaceFirstChar { it.uppercase() }
    }

    private fun configurarBottomNav() {
        bottom_nav.selectedItemId = R.id.nav_menu
    }

    private fun ejecutarAbrirProductos(v: View) {
        val intent = Intent(this, inventario::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)
    }

    private fun ejecutarAbrirReportes(v: View) {
        val intent = Intent(this, ReportesVentaspro::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)
    }

    private fun ejecutarSalir(v: View) {
        presentadorInicioAdmin.cerrarSesion()
    }

    private fun ejecutarNavegacionInferior(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_menu -> true
            R.id.nav_inventario -> {
                val intent = Intent(this, inventario::class.java)
                intent.putExtra("id_usuario", idUsuario)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    fun mostrarInicioAdmin(respuesta: InicioAdminResponse) {
        val montoHoy = respuesta.ventas?.hoy?.monto ?: 0.0
        val pedidosPorAtender = respuesta.pedidos?.porAtender ?: 0
        val stockBajo = respuesta.productos?.totalStockBajo ?: 0

        tvVentasHoy.text = "$" + montoHoy
        tvPedidos.text = pedidosPorAtender.toString()
        tvStockBajo.text = stockBajo.toString()
    }

    fun mostrarErrorInicioAdmin(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun irALogin() {
        startActivity(
            Intent(this, inicio_de_sesion::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }
}