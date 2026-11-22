package com.example.mitiendita.vista

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.R
import com.example.mitiendita.modelo.dataProductoInventario
import com.example.mitiendita.presentador.clsInventarioPresentador
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class inventario : AppCompatActivity() {

    private lateinit var txtBuscar2: EditText
    private lateinit var btnTodos: MaterialButton
    private lateinit var btnStockBajo: MaterialButton
    private lateinit var rvInventario: RecyclerView
    private lateinit var fabAgregar: FloatingActionButton
    private lateinit var bottom_nav: BottomNavigationView
    private lateinit var adaptador: InventarioAdaptador
    private lateinit var presentador: clsInventarioPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inventario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtBuscar2 = findViewById(R.id.txtBuscar2)
        btnTodos = findViewById(R.id.btnTodos)
        btnStockBajo = findViewById(R.id.btnStockBajo)
        rvInventario = findViewById(R.id.rvInventario)
        fabAgregar = findViewById(R.id.fabAgregar)
        bottom_nav = findViewById(R.id.bottom_nav)

        rvInventario.layoutManager = LinearLayoutManager(this)

        adaptador = InventarioAdaptador(emptyList())
        rvInventario.adapter = adaptador

        presentador = clsInventarioPresentador(this)

        btnTodos.setOnClickListener {
            txtBuscar2.text.clear()
            presentador.cargarInventario()
        }

        btnStockBajo.setOnClickListener {
            txtBuscar2.text.clear()
            presentador.cargarStockBajo()
        }

        txtBuscar2.addTextChangedListener(object : android.text.TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presentador.buscarProducto(s.toString())
            }

            override fun afterTextChanged(s: android.text.Editable?) {
            }
        })

        presentador.iniciar()
    }

    fun mostrarProductos(productos: List<dataProductoInventario>) {
        adaptador.actualizarLista(productos)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}