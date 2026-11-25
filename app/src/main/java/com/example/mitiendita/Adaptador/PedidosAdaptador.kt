package com.example.mitiendita.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mitiendita.R
import com.example.mitiendita.modelo.Pedido

class PedidosAdaptador(
    private var listaPedidos: List<Pedido>
) : RecyclerView.Adapter<PedidosAdaptador.PedidoViewHolder>() {

    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNumeroPedido: TextView = itemView.findViewById(R.id.txtNumeroPedido)
        val txtEstadoPedido: TextView = itemView.findViewById(R.id.txtEstadoPedido)
        val txtClientePedido: TextView = itemView.findViewById(R.id.txtClientePedido)
        val txtTotalPedido: TextView = itemView.findViewById(R.id.txtTotalPedido)
        val txtDetallesPedido: TextView = itemView.findViewById(R.id.txtDetallesPedido)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)

        return PedidoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = listaPedidos[position]

        holder.txtNumeroPedido.text = "Pedido #${pedido.id_pedido}"
        holder.txtEstadoPedido.text = pedido.estado.replaceFirstChar { it.uppercase() }
        holder.txtClientePedido.text = pedido.cliente
        holder.txtTotalPedido.text = "$${String.format("%.2f", pedido.total)}"
        holder.txtDetallesPedido.text =
            "${pedido.total_articulos} artículos · ${pedido.tipo_entrega} · ${pedido.fecha_pedido}"
    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    fun actualizarLista(nuevaLista: List<Pedido>) {
        listaPedidos = nuevaLista
        notifyDataSetChanged()
    }
}