package com.example.mitiendita.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.mitiendita.R
import com.example.mitiendita.presentador.clsPedidoRegistradoPresentador
import com.google.android.material.button.MaterialButton

class pedido_registrado : AppCompatActivity() {

    private lateinit var lottieExito: LottieAnimationView
    private lateinit var tvNumeroPedido: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnVerPedidos: MaterialButton
    private lateinit var btnSeguirComprando: MaterialButton

    private lateinit var presentador: clsPedidoRegistradoPresentador

    private var idUsuario: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedido_registrado)

        lottieExito = findViewById(R.id.lottieExito)
        tvNumeroPedido = findViewById(R.id.tvNumeroPedido)
        tvTotal = findViewById(R.id.tvTotal)
        btnVerPedidos = findViewById(R.id.btnVerPedidos)
        btnSeguirComprando = findViewById(R.id.btnSeguirComprando)

        presentador = clsPedidoRegistradoPresentador(vista = this)

        btnVerPedidos.setOnClickListener(this::ejecutarVerPedidos)
        btnSeguirComprando.setOnClickListener(this::ejecutarSeguirComprando)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootPedidoRegistrado)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ejecutarCargarDatos()
        presentador.iniciarAnimacionTresSegundos()
    }

    private fun ejecutarCargarDatos() {
        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))
        val idPedido = intent.getIntExtra("id_pedido", 0)
        val folio = intent.getStringExtra("folio_pedido")
        val total = intent.getDoubleExtra("total_pedido", intent.getDoubleExtra("total", 0.0))

        presentador.cargarPedidoRegistrado(idUsuario, idPedido, folio, total)
    }

    private fun ejecutarVerPedidos(v: View) {
        val intent = Intent(this, VistaMisPedidos::class.java).apply {
            putExtra("id_usuario", presentador.obtenerIdUsuario())
            putExtra("idUsuario", presentador.obtenerIdUsuario())
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun ejecutarSeguirComprando(v: View) {
        val intent = Intent(this, Busqueda_de_producto::class.java).apply {
            putExtra("id_usuario", presentador.obtenerIdUsuario())
            putExtra("idUsuario", presentador.obtenerIdUsuario())
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }

    fun pintarPedido(folio: String, total: Double) {
        tvNumeroPedido.text = folio
        tvTotal.text = "$" + total
    }

    fun iniciarLottie(duracionMs: Long) {
        try {
            val original = lottieExito.duration
            lottieExito.speed = if (original > 0L) original.toFloat() / duracionMs.toFloat() else 1.0f
            lottieExito.progress = 0f
            lottieExito.playAnimation()
        } catch (_: Exception) {
        }
    }

    fun terminarLottie() {
        try {
            lottieExito.pauseAnimation()
            lottieExito.progress = 1f
        } catch (_: Exception) {
        }
    }

    fun mostrarMensajeError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}