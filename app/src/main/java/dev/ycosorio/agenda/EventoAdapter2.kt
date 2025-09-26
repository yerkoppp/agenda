package dev.ycosorio.agenda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ycosorio.agenda.databinding.ItemTareaBinding


class EventoAdapter2(private val eventos: List<Evento>) :
    RecyclerView.Adapter<EventoAdapter2.EventoViewHolder>() {



    inner class EventoViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Si usas View Binding para la fila (recomendado), no necesitas findViewById
        // Las vistas son accesibles directamente via 'binding'
    }

    // Y ajusta onCreateViewHolder() para inflar usando View Binding:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventoViewHolder(binding)
    }


    // (Punto 2c: Conectar el objeto Evento con las vistas de la fila)
    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        // 1. Obtener el objeto Evento en esta posición
        val evento = eventos[position]

        // 2. Usar el 'holder' para acceder a las vistas y asignar los datos
        // (Ejemplo usando View Binding, asumiendo que tienes un TextView con id 'tvTitulo' y otro 'tvHora' en item_tarea.xml)
        holder.binding.tvTitulo.text = evento.titulo // Aquí se pone el título real
        holder.binding.tcHora.text = evento.hora     // Aquí se pone la hora real
        holder.binding.tvDescripcion.text = evento.descripcion

        // Si tienes un dato de tipo Boolean (ej: completado)
        // holder.binding.checkBoxCompletado.isChecked = evento.estaCompletado
    }

    // (Punto 2d: Decir cuántos elementos hay)
    override fun getItemCount() = eventos.size
}