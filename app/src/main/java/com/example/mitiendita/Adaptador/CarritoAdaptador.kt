package com.example.mitiendita.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitiendita.R
import com.example.mitiendita.modelo.CarritoItem
import com.example.mitiendita.vista.carrito

class CarritoAdaptador(
    private val activity: carrito,
    private var listaCarrito: List<CarritoItem>,
    private val onCambiarCantidad: (item: CarritoItem, nuevaCantidad: Int) -> Unit
) : RecyclerView.Adapter<CarritoAdaptador.CarritoViewHolder>() {

    class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProducto: ImageView = itemView.findViewById(R.id.ivProducto)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPresentacion: TextView = itemView.findViewById(R.id.tvPresentacion)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val btnMenos: TextView = itemView.findViewById(R.id.btnMenos)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val btnMas: TextView = itemView.findViewById(R.id.btnMas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = listaCarrito[position]

        holder.tvNombre.text = item.nombreProducto
        holder.tvPresentacion.text = item.descripcion ?: ""
        holder.tvPresentacion.visibility =
            if (item.descripcion.isNullOrBlank()) View.GONE else View.VISIBLE

        val subtotal = if (item.subtotal > 0.0) item.subtotal else (item.precioV * item.cantidad)
        holder.tvPrecio.text = "$" + subtotal
        holder.tvCantidad.text = item.cantidad.toString()

        val urlImagenCompleta = construirUrlImagen(item.imagen)

        Glide.with(holder.itemView.context)
            .load(urlImagenCompleta)
            .placeholder(R.drawable.subir_imagen)
            .error(R.drawable.subir_imagen)
            .into(holder.ivProducto)

        holder.btnMenos.setOnClickListener {
            onCambiarCantidad(item, item.cantidad - 1)
        }

        holder.btnMas.setOnClickListener {
            if (item.cantidad < item.stockAct) {
                onCambiarCantidad(item, item.cantidad + 1)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaCarrito.size
    }

    fun actualizarLista(nuevaLista: List<CarritoItem>) {
        listaCarrito = nuevaLista
        notifyDataSetChanged()
    }

    private fun construirUrlImagen(ruta: String?): String? {
        if (ruta.isNullOrBlank()) return null
        val baseUrl = "https://yenisey.4buthh.com/miTiendita/raiz/"
        return if (ruta.startsWith("http")) ruta else baseUrl + ruta.removePrefix("/")
    }
}