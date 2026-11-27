package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.Adaptador.ResumenPedidoAdaptador
import com.example.mitiendita.R
import com.example.mitiendita.modelo.CarritoResponse
import com.example.mitiendita.modelo.CrearPedidoRequest
import com.example.mitiendita.presentador.clsConfirmarPedidoPresentador
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class confirmarPedido : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var rvResumen: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var cardEfectivo: MaterialCardView
    private lateinit var rbEfectivo: RadioButton
    private lateinit var btnConfirmarPedido: MaterialButton

    private lateinit var presentador: clsConfirmarPedidoPresentador
    private lateinit var adaptadorResumen: ResumenPedidoAdaptador

    private var idUsuario: Int = 1
    private var totalActual: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmar_pedido)

        btnAtras = findViewById(R.id.btnAtras)
        rvResumen = findViewById(R.id.rvResumen)
        tvTotal = findViewById(R.id.tvTotal)
        cardEfectivo = findViewById(R.id.cardEfectivo)
        rbEfectivo = findViewById(R.id.rbEfectivo)
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido)

        presentador = clsConfirmarPedidoPresentador(vista = this)

        btnAtras.setOnClickListener(this::ejecutarAtras)
        cardEfectivo.setOnClickListener(this::ejecutarSeleccionarEfectivo)
        btnConfirmarPedido.setOnClickListener(this::ejecutarConfirmarPedido)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootconfirmar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarRecycler()
        ejecutarCargarResumen()
    }

    private fun configurarRecycler() {
        rvResumen.layoutManager = LinearLayoutManager(this)
        adaptadorResumen = ResumenPedidoAdaptador(emptyList())
        rvResumen.adapter = adaptadorResumen
    }

    private fun ejecutarCargarResumen() {
        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))
        presentador.cargarResumen(idUsuario)
    }

    private fun ejecutarAtras(v: View) {
        finish()
    }

    private fun ejecutarSeleccionarEfectivo(v: View) {
        rbEfectivo.isChecked = true
    }

    private fun ejecutarConfirmarPedido(v: View) {
        if (adaptadorResumen.itemCount == 0) {
            Toast.makeText(this, "No hay productos en el resumen", Toast.LENGTH_SHORT).show()
            return
        }

        val request = CrearPedidoRequest(
            id_usuario = idUsuario,
            id_metodo_pago = if (rbEfectivo.isChecked) 1 else 1,
            tipo_entrega = "recoger"
        )
        presentador.confirmarPedido(request)
    }

    fun mostrarResumen(respuesta: CarritoResponse) {
        val lista = respuesta.carrito
        adaptadorResumen.actualizarLista(lista)

        val totalCalculado = if (respuesta.total > 0.0) {
            respuesta.total
        } else {
            lista.sumOf { if (it.subtotal > 0.0) it.subtotal else (it.precioV * it.cantidad) }
        }

        totalActual = totalCalculado
        tvTotal.text = "$" + String.format("%.2f", totalCalculado)
        btnConfirmarPedido.isEnabled = lista.isNotEmpty()
    }

    fun mostrarCargando(cargando: Boolean) {
        btnConfirmarPedido.isEnabled = !cargando
    }

    fun pedidoRegistrado(
        folio: String,
        metodoPagoTexto: String,
        estado: String,
        total: Double
    ) {
        val montoFinal = if (total > 0.0) total else totalActual

        val intent = Intent(this, pedido_registrado::class.java).apply {
            putExtra("id_usuario", idUsuario)
            putExtra("idUsuario", idUsuario)
            putExtra("folio_pedido", folio)
            putExtra("total_pedido", montoFinal)
        }
        startActivity(intent)
        finish()
    }

    fun mostrarMensajeError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}