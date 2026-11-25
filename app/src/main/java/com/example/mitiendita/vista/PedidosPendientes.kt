package com.example.mitiendita.vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.Adaptador.PedidosAdaptador
import com.example.mitiendita.R
import com.example.mitiendita.modelo.Pedido
import com.example.mitiendita.presentador.clsPedidosPresentador

class PedidosPendientes : AppCompatActivity() {

    private lateinit var rvPedidos: RecyclerView
    private lateinit var adaptador: PedidosAdaptador
    private lateinit var presentador: clsPedidosPresentador

    private lateinit var tvPendientes: TextView
    private lateinit var tvListos: TextView
    private lateinit var tvEntregados: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedidos_pendientes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPedidos = findViewById(R.id.rvPedidos)
        tvPendientes = findViewById(R.id.tvPendientes)
        tvListos = findViewById(R.id.tvListos)
        tvEntregados = findViewById(R.id.tvEntregados)

        rvPedidos.layoutManager = LinearLayoutManager(this)

        adaptador = PedidosAdaptador(emptyList())
        rvPedidos.adapter = adaptador

        presentador = clsPedidosPresentador(this)

        tvPendientes.setOnClickListener {
            presentador.obtenerPedidos("pendiente")
        }

        tvListos.setOnClickListener {
            presentador.obtenerPedidos("listo")
        }

        tvEntregados.setOnClickListener {
            presentador.obtenerPedidos("entregado")
        }

        presentador.iniciar()
    }

    fun mostrarPedidos(lista: List<Pedido>) {
        adaptador.actualizarLista(lista)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}