package com.example.mitiendita.vista

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.R
import com.example.mitiendita.modelo.dataProductoInventario
import com.google.android.material.button.MaterialButton

class InventarioAdaptador(
    private var listaProductos: List<dataProductoInventario>
) : RecyclerView.Adapter<InventarioAdaptador.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCodigo2: TextView = itemView.findViewById(R.id.tvCodigo2)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPrecio2: TextView = itemView.findViewById(R.id.tvPrecio2)
        val tvStock: TextView = itemView.findViewById(R.id.tvStock)
        val tvEstado: MaterialButton = itemView.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventario, parent, false)

        return ProductoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.tvCodigo2.text = "ID: ${producto.idProducto}"
        holder.tvNombre.text = producto.nombreProducto
        holder.tvPrecio2.text = "$${producto.precioV}"
        holder.tvStock.text = producto.stockAct.toString()

        when {
            producto.stockAct == 0 -> {
                holder.tvEstado.text = "Agotado"
                holder.tvEstado.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.parseColor("#FCE4E4"))
                )
                holder.tvEstado.setTextColor(Color.parseColor("#C62828"))
            }

            producto.stockBajo -> {
                holder.tvEstado.text = "Stock bajo"
                holder.tvEstado.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.parseColor("#FFF0D9"))
                )
                holder.tvEstado.setTextColor(Color.parseColor("#B76E00"))
            }

            producto.estado == "Inactivo" -> {
                holder.tvEstado.text = "Inactivo"
                holder.tvEstado.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.parseColor("#EEEEEE"))
                )
                holder.tvEstado.setTextColor(Color.parseColor("#616161"))
            }

            else -> {
                holder.tvEstado.text = "Normal"
                holder.tvEstado.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(Color.parseColor("#DDF1E6"))
                )
                holder.tvEstado.setTextColor(Color.parseColor("#0B5D4B"))
            }
        }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    fun actualizarLista(nuevaLista: List<dataProductoInventario>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}