package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootconfirmar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnAtras = findViewById(R.id.btnAtras)
        rvResumen = findViewById(R.id.rvResumen)
        tvTotal = findViewById(R.id.tvTotal)
        cardEfectivo = findViewById(R.id.cardEfectivo)
        rbEfectivo = findViewById(R.id.rbEfectivo)
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido)

        presentador = clsConfirmarPedidoPresentador(this)
        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))

        rvResumen.layoutManager = LinearLayoutManager(this)
        adaptadorResumen = ResumenPedidoAdaptador(emptyList())
        rvResumen.adapter = adaptadorResumen

        btnAtras.setOnClickListener { finish() }

        cardEfectivo.setOnClickListener {
            rbEfectivo.isChecked = true
        }

        btnConfirmarPedido.setOnClickListener {
            if (adaptadorResumen.itemCount == 0) {
                Toast.makeText(this, "No hay productos en el resumen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Arma el request para registrar el pedido como pendiente
            val request = CrearPedidoRequest(
                id_usuario = idUsuario,
                id_metodo_pago = if (rbEfectivo.isChecked) 1 else 1,
                tipo_entrega = "recoger"
            )
            presentador.confirmarPedido(request)
        }

        presentador.cargarResumen(idUsuario)
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
        Toast.makeText(
            this,
            "¡Pedido registrado! $folio · Estado: $estado · Total: $${String.format("%.2f", montoFinal)}",
            Toast.LENGTH_LONG
        ).show()

        //
        val intent = Intent(this, VistaMisPedidos::class.java).apply {
            putExtra("id_usuario", idUsuario)
            putExtra("folio_pedido", folio)
            putExtra("estado_pedido", estado)
            putExtra("metodo_pago", metodoPagoTexto)
            putExtra("total_pedido", montoFinal)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }

    fun mostrarMensajeError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}