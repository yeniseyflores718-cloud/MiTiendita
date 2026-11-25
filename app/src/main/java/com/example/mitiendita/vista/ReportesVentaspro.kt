package com.example.mitiendita.vista

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.Adaptador.ProductosMasVendidosAdaptador
import com.example.mitiendita.R
import com.example.mitiendita.modelo.ReportesResponse
import com.example.mitiendita.presentador.clsReportesPresentador
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Collections.emptyList

class ReportesVentaspro : AppCompatActivity() {
    private lateinit var btnRegresar: MaterialCardView
    private lateinit var btnFiltroSemana: MaterialButton
    private lateinit var btnFiltroMes: MaterialButton
    private lateinit var txtMontoTotalVentas: TextView
    private lateinit var txtTotalVentas: TextView
    private lateinit var txtTotalClientes: TextView
    private lateinit var txtTotalProductosVendidos: TextView
    private lateinit var rvProductosMasVendidos: RecyclerView

    private lateinit var presentador: clsReportesPresentador
    private lateinit var adaptadorProductos_R: ProductosMasVendidosAdaptador

    private var idUsuario: Int = 1
    private var periodoActual: String = "semana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reportes_ventaspro)

        btnRegresar = findViewById(R.id.btnRegresar)
        btnFiltroSemana = findViewById(R.id.btnFiltroSemana)
        btnFiltroMes = findViewById(R.id.btnFiltroMes)
        txtMontoTotalVentas = findViewById(R.id.txtMontoTotalVentas)
        txtTotalVentas = findViewById(R.id.txtTotalVentas)
        txtTotalClientes = findViewById(R.id.txtTotalClientes)
        txtTotalProductosVendidos = findViewById(R.id.txtTotalProductosVendidos)
        rvProductosMasVendidos = findViewById(R.id.rvProductosMasVendidos)

        presentador = clsReportesPresentador(this)

        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))

        rvProductosMasVendidos.layoutManager = LinearLayoutManager(this)
        adaptadorProductos_R = ProductosMasVendidosAdaptador(this, emptyList())
        rvProductosMasVendidos.adapter = adaptadorProductos_R

        btnRegresar.setOnClickListener { finish() }
        btnFiltroSemana.setOnClickListener { seleccionarPeriodo("semana") }
        btnFiltroMes.setOnClickListener { seleccionarPeriodo("mes") }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        seleccionarPeriodo("semana")
    }

    private fun seleccionarPeriodo(periodo: String) {
        periodoActual = periodo
        actualizarEstilosFiltro(periodo)
        presentador.cargarReportes(idUsuario, periodo)
    }

    private fun actualizarEstilosFiltro(periodo: String) {
        if (periodo == "semana") {
            btnFiltroSemana.setBackgroundColor(Color.parseColor("#DCEEF9"))
            btnFiltroSemana.setTextColor(Color.parseColor("#1677D2"))
            btnFiltroSemana.strokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#1677D2"))

            btnFiltroMes.setBackgroundColor(Color.parseColor("#FFFFFF"))
            btnFiltroMes.setTextColor(Color.parseColor("#8A8A8A"))
            btnFiltroMes.strokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#EAEAEA"))
        } else {
            btnFiltroMes.setBackgroundColor(Color.parseColor("#DCEEF9"))
            btnFiltroMes.setTextColor(Color.parseColor("#1677D2"))
            btnFiltroMes.strokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#1677D2"))

            btnFiltroSemana.setBackgroundColor(Color.parseColor("#FFFFFF"))
            btnFiltroSemana.setTextColor(Color.parseColor("#8A8A8A"))
            btnFiltroSemana.strokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#EAEAEA"))
        }
    }

    fun mostrarReporte(respuesta: ReportesResponse) {
        val resumen = respuesta.resumen
        if (resumen != null) {
            txtMontoTotalVentas.text = "$" + resumen.montoTotal
            txtTotalVentas.text = resumen.totalVentas.toString()
            txtTotalClientes.text = resumen.clientesAtendidos.toString()
            txtTotalProductosVendidos.text = resumen.productosVendidos.toString()
        }

        adaptadorProductos_R.actualizarLista(respuesta.productosMasVendidos ?: emptyList())
    }

    fun mostrarMensajeError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}