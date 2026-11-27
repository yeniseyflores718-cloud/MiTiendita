package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.Adaptador.CarritoAdaptador
import com.example.mitiendita.R
import com.example.mitiendita.modelo.CarritoResponse
import com.example.mitiendita.presentador.clsCarritoPresentador
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class carrito : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var tvTitulo: TextView
    private lateinit var rvCarrito: RecyclerView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnContinuar: MaterialButton
    private lateinit var bottomNavCliente: BottomNavigationView

    private lateinit var presentador: clsCarritoPresentador
    private lateinit var adaptadorCarrito: CarritoAdaptador

    private var idUsuario: Int = 1
    private var totalActual: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)

        btnAtras = findViewById(R.id.btnAtras)
        tvTitulo = findViewById(R.id.tvTitulo)
        rvCarrito = findViewById(R.id.rvCarrito)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvTotal = findViewById(R.id.tvTotal)
        btnContinuar = findViewById(R.id.btnContinuar)
        bottomNavCliente = findViewById(R.id.bottomNavCliente)

        presentador = clsCarritoPresentador(vista = this)

        btnAtras.setOnClickListener(this::ejecutarAtras)
        btnContinuar.setOnClickListener(this::ejecutarContinuar)
        bottomNavCliente.setOnItemSelectedListener(this::ejecutarNavegacionInferior)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ejecutarCargarCarrito()
        configurarRecycler()
        configurarBottomNav()
    }

    private fun ejecutarCargarCarrito() {
        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))
        presentador.cargarCarrito(idUsuario)
    }

    private fun configurarRecycler() {
        rvCarrito.layoutManager = LinearLayoutManager(this)
        adaptadorCarrito = CarritoAdaptador(this, emptyList()) { item, nuevaCantidad ->
            if (nuevaCantidad <= 0) {
                presentador.eliminarProducto(idUsuario, item.idProducto)
            } else {
                presentador.actualizarCantidad(idUsuario, item.idProducto, nuevaCantidad)
            }
        }
        rvCarrito.adapter = adaptadorCarrito
    }

    private fun configurarBottomNav() {
        bottomNavCliente.selectedItemId = R.id.nav_carrito
    }

    private fun ejecutarAtras(v: View) {
        finish()
    }

    private fun ejecutarContinuar(v: View) {
        if (adaptadorCarrito.itemCount == 0) {
            Toast.makeText(this, "Tu carrito está vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, confirmarPedido::class.java).apply {
            putExtra("id_usuario", idUsuario)
            putExtra("total", totalActual)
        }
        startActivity(intent)
    }

    private fun ejecutarNavegacionInferior(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_catalogo -> { abrir(Busqueda_de_producto::class.java); true }
            R.id.nav_carrito -> true
            R.id.nav_pedidos -> { abrir(VistaMisPedidos::class.java); true }
            R.id.nav_cuenta -> { abrir(CuentaCliente::class.java); true }
            else -> false
        }
    }

    fun mostrarCarrito(respuesta: CarritoResponse) {
        val lista = respuesta.carrito
        adaptadorCarrito.actualizarLista(lista)

        val totalCalculado = if (respuesta.total > 0.0) {
            respuesta.total
        } else {
            lista.sumOf { if (it.subtotal > 0.0) it.subtotal else (it.precioV * it.cantidad) }
        }

        totalActual = totalCalculado
        val textoMonto = "$" + totalCalculado
        tvSubtotal.text = textoMonto
        tvTotal.text = textoMonto
        btnContinuar.isEnabled = lista.isNotEmpty()
    }

    fun mostrarMensajeError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino))
        overridePendingTransition(0, 0)
        finish()
    }
}