package dev.ycosorio.agenda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ycosorio.agenda.databinding.ItemTareaBinding
import java.time.format.DateTimeFormatter
import java.util.*

class EventoAdapter2(private val eventos: List<Evento>) :
    RecyclerView.Adapter<EventoAdapter2.EventoViewHolder>() {

    inner class EventoViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]

        // MANEJO SEGURO DE NULLABLES
        holder.binding.tvTitulo.text = evento.titulo ?: "Sin título"
        holder.binding.tcHora.text = evento.hora?.toString() ?: "Sin hora"
        holder.binding.tvDescripcion.text = evento.descripcion ?: "Sin descripción"

        // FORMATO CONSISTENTE PARA FECHA
        holder.binding.tvFecha.text = evento.fecha?.let { fecha ->
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
            fecha.format(formatter)
        } ?: "Sin fecha"
    }

    override fun getItemCount() = eventos.size
}