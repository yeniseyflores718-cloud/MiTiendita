package com.example.mitiendita.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitiendita.R
import com.example.mitiendita.modelo.Producto
import com.example.mitiendita.modelo.RetrofitCliente.BASE_URL_IMG

class ProductosAdaptador(
    private var listaProductos: List<Producto>,
    private val onAgregar: (Producto) -> Unit
) : RecyclerView.Adapter<ProductosAdaptador.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val txtNombreProducto: TextView = itemView.findViewById(R.id.txtNombreProducto)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        //val btnAgregar: Button = itemView.findViewById(R.id.btnAgregar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carta_producto, parent, false)

        return ProductoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {

        val producto = listaProductos[position]

        holder.txtNombreProducto.text = producto.nombreProducto
        holder.txtDescripcion.text = producto.descripcion
        holder.txtPrecio.text = "$${producto.precioV}"

        Glide.with(holder.itemView.context)
            .load(BASE_URL_IMG + producto.imagen)
            .placeholder(R.drawable.catalogo)
            .error(R.drawable.catalogo)
            .into(holder.imgProducto)
        holder.imgProducto.setOnClickListener {
            onAgregar(producto)
        }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    fun actualizarLista(nuevaLista: List<Producto>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}