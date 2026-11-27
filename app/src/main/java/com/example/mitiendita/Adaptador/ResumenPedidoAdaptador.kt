package com.example.mitiendita.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.R
import com.example.mitiendita.modelo.CarritoItem // Usa aquí el nombre de tu clase de ítem de carrito

class ResumenPedidoAdaptador(
    private var lista: List<CarritoItem>
) : RecyclerView.Adapter<ResumenPedidoAdaptador.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvImporte: TextView = view.findViewById(R.id.tvImporte)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resumen_pedido, parent, false)
        return ViewHolder(vista)
    }

    //
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        val subtotal = if (item.subtotal > 0.0) item.subtotal else (item.precioV * item.cantidad)

        // Muestra cantidad × nombre del producto e importe formateado
        holder.tvDescripcion.text = "${item.cantidad}× ${item.nombreProducto}"
        holder.tvImporte.text = "$" + String.format("%.2f", subtotal)
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<CarritoItem>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}