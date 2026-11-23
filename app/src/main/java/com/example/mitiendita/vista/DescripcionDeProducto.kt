package com.example.mitiendita.vista

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.mitiendita.R
import com.example.mitiendita.modelo.Producto
import com.example.mitiendita.modelo.RetrofitCliente.BASE_URL_IMG
import com.example.mitiendita.presentador.clsDetalleProductoPresentador

class DescripcionDeProducto : AppCompatActivity() {

    private lateinit var presentador: clsDetalleProductoPresentador

    private lateinit var imageView3: ImageView
    private lateinit var textView12: TextView
    private lateinit var txtViewEstado: TextView
    private lateinit var textView14: TextView

    private lateinit var txtPrecioProducto: TextView
    private lateinit var btnAtras: com.google.android.material.card.MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.descripcion_de_producto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView3 = findViewById(R.id.imageView3)
        textView12 = findViewById(R.id.textView12)
        txtViewEstado = findViewById(R.id.txtViewEstado)
        textView14 = findViewById(R.id.textView14)
        txtPrecioProducto = findViewById(R.id.txtPrecioProducto)
        btnAtras = findViewById(R.id.btnAtras)

        presentador = clsDetalleProductoPresentador(this)

        val idProducto = intent.getIntExtra("idProducto", 0)

        if (idProducto != 0) {
            presentador.obtenerProducto(idProducto)
        } else {
            mostrarError("No se recibió el ID del producto")
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    fun mostrarProducto(producto: Producto) {
        textView12.text = producto.nombreProducto

        txtPrecioProducto.text = "$${producto.precioV}"

        if (producto.stockAct > 0) {
            txtViewEstado.text = "En existencia"
        } else {
            txtViewEstado.text = "Agotado"
        }

        textView14.text = producto.descripcion.ifEmpty {
            "Sin descripción disponible"
        }

        val rutaImagen = producto.imagen ?: producto.imagenes.firstOrNull()?.ruta

        Glide.with(this)
            .load(rutaImagen?.let { BASE_URL_IMG + it })
            .placeholder(R.drawable.catalogo)
            .error(R.drawable.catalogo)
            .into(imageView3)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}