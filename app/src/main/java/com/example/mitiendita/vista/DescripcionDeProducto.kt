package com.example.mitiendita.vista

import android.os.Bundle
import android.view.View
import android.widget.Button
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
import com.example.mitiendita.modelo.clsCarritoModelo
import com.example.mitiendita.presentador.clsDetalleProductoPresentador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DescripcionDeProducto : AppCompatActivity() {
    //

    private lateinit var presentador: clsDetalleProductoPresentador
    private val modeloCarrito = clsCarritoModelo()

    private lateinit var imageView3: ImageView
    private lateinit var textView12: TextView
    private lateinit var txtViewEstado: TextView
    private lateinit var textView14: TextView
    private lateinit var txtPrecioProducto: TextView
    private lateinit var btnAtras: com.google.android.material.card.MaterialCardView
    private lateinit var btnAgregarCarrito: Button

    private var idProducto: Int = 0
    private var idUsuario: Int = 1
    private var stockActual: Int = 0

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
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito)

        presentador = clsDetalleProductoPresentador(this)

        idProducto = intent.getIntExtra("idProducto", 0)
        idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))

        if (idProducto != 0) {
            presentador.obtenerProducto(idProducto)
        } else {
            mostrarError("No se recibió el ID del producto")
        }

        btnAtras.setOnClickListener {
            finish()
        }

        btnAgregarCarrito.setOnClickListener(this::ejecutarAgregarCarrito)
    }

    fun mostrarProducto(producto: Producto) {
        stockActual = producto.stockAct
        textView12.text = producto.nombreProducto
        txtPrecioProducto.text = "$" + producto.precioV

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

    fun ejecutarAgregarCarrito(v: View) {
        if (!validarDatosCarrito()) return

        guardarProductoEnCarrito()
    }

    private fun validarDatosCarrito(): Boolean {
        if (idProducto <= 0) {
            mostrarError("Producto no válido")
            return false
        }
        if (stockActual <= 0) {
            mostrarError("Producto agotado")
            return false
        }
        return true
    }

    private fun guardarProductoEnCarrito() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modeloCarrito.agregarProducto(idUsuario, idProducto, 1)
                }

                if (respuesta.exito) {
                    Toast.makeText(
                        this@DescripcionDeProducto,
                        respuesta.mensaje ?: "Producto agregado al carrito",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mostrarError(respuesta.mensaje ?: "No se pudo agregar el producto")
                }
            } catch (e: Exception) {
                mostrarError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}