package com.example.entregapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica02_ruizcoloma.R
import com.example.practica02_ruizcoloma.models.JugadorModel

/**
 * jesus
 */
class JugadorAdapter(private var lstJugadores: List<JugadorModel>)
    : RecyclerView.Adapter<JugadorAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(jugador: JugadorModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreJugador: TextView = itemView.findViewById(R.id.tvNombreJugador)
        val tvDorsal: TextView = itemView.findViewById(R.id.tvDorsal)
        val tvPais: TextView = itemView.findViewById(R.id.tvPais)
        val tvPosicion: TextView = itemView.findViewById(R.id.tvPosicion)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(lstJugadores[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_jugador, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jugador = lstJugadores[position]
        holder.tvNombreJugador.text = jugador.nombreJugador
        holder.tvDorsal.text = jugador.dorsal
        holder.tvPais.text = jugador.pais
        holder.tvPosicion.text = jugador.posicion
    }

    override fun getItemCount(): Int {
        return lstJugadores.size
    }
}