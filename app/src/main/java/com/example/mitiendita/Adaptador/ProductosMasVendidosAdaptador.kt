package com.example.mitiendita.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitiendita.R
import com.example.mitiendita.modelo.dataProductoMasVendido
import com.example.mitiendita.vista.ReportesVentaspro

class ProductosMasVendidosAdaptador(
    private val activity: ReportesVentaspro,
    private var listaProductos: List<dataProductoMasVendido>
) : RecyclerView.Adapter<ProductosMasVendidosAdaptador.ProductosViewHolder>() {

    class ProductosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProductoVendido: ImageView = itemView.findViewById(R.id.imgProductoVendido)
        val txtNombreProductoVendido: TextView = itemView.findViewById(R.id.txtNombreProductoVendido)
        val txtPiezasVendidas: TextView = itemView.findViewById(R.id.txtPiezasVendidas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_mas_vendido, parent, false)
        return ProductosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.txtNombreProductoVendido.text = producto.nombreProducto
        holder.txtPiezasVendidas.text = "${producto.unidades} pzas"

        val baseUrl = "https://yenisey.4buthh.com/miTiendita/raiz/"
        val urlImagenCompleta = if (!producto.imagen.isNullOrBlank()) {
            baseUrl + producto.imagen
        } else {
            null
        }

        Glide.with(holder.itemView.context)
            .load(urlImagenCompleta)
            .placeholder(R.drawable.subir_imagen)
            .error(R.drawable.subir_imagen)
            .into(holder.imgProductoVendido)
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    fun actualizarLista(nuevaLista: List<dataProductoMasVendido>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}