package com.example.mitiendita.vista

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.Adaptador.ProductosAdaptador
import com.example.mitiendita.R
import com.example.mitiendita.modelo.Producto
import com.example.mitiendita.modelo.dataCategorias
import com.example.mitiendita.presentador.clsBusquedaProductoPresentador

class Busqueda_de_producto : AppCompatActivity() {

    private lateinit var spinnerCategorias: Spinner
    private lateinit var editTxtbuscadordeproducto: EditText
    private lateinit var recyclerProductos: RecyclerView

    private lateinit var adaptador: ProductosAdaptador

    private lateinit var presentador: clsBusquedaProductoPresentador

    private var cargandoSpinner = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busqueda_de_producto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerCategorias = findViewById(R.id.spinnerCategorias)
        editTxtbuscadordeproducto = findViewById(R.id.editTxtbuscadordeproducto)
        recyclerProductos = findViewById(R.id.recyclerProductos)

        recyclerProductos.layoutManager = GridLayoutManager(this, 2)

        adaptador = ProductosAdaptador(emptyList()) { producto ->
            Toast.makeText(this, producto.nombreProducto, Toast.LENGTH_SHORT).show()
        }

        recyclerProductos.adapter = adaptador

        presentador = clsBusquedaProductoPresentador(this)

        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!cargandoSpinner) {
                    ejecutarBusqueda()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        editTxtbuscadordeproducto.addTextChangedListener(object :
            android.text.TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ejecutarBusqueda()
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        presentador.iniciar()
    }

    fun ejecutarBusqueda() {
        if (::presentador.isInitialized && spinnerCategorias.selectedItemPosition >= 0) {
            presentador.seleccionarCategoria(
                spinnerCategorias.selectedItemPosition,
                editTxtbuscadordeproducto.text.toString()
            )
        }
    }

    fun mostrarCategorias(categorias: List<dataCategorias>) {

        val nombresCategorias = mutableListOf("Todas")

        for (categoria in categorias) {
            nombresCategorias.add(categoria.categoria)
        }

        val adaptadorSpinner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombresCategorias
        )

        adaptadorSpinner.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        cargandoSpinner = true
        spinnerCategorias.adapter = adaptadorSpinner
        cargandoSpinner = false
    }

    fun mostrarProductos(productos: List<Producto>) {
        adaptador.actualizarLista(productos)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}