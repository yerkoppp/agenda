package dev.ycosorio.agenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;



import java.util.List;


public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    // Lista de datos que se mostrará en el RecyclerView
    private final List<Evento> listaEventos;

    public EventoAdapter(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    // 1. Interfaz para manejar el clic del Checkbox
    public interface OnEventoSeleccionadoListener {
        void onLibroSeleccionado(Evento evento, boolean isSeleccionado);
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar (convertir de XML a View) el layout del item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);

        // Crear y retornar un nuevo ViewHolder con esta vista
        return new EventoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        // Obtener el libro específico de esta posición
        Evento evento = listaEventos.get(position);

        // Llenar las vistas con los datos del libro
        holder.tvTitulo.setText(evento.getTitulo());
        holder.tvHora.setText(evento.getHora());
        holder.tvFecha.setText(evento.getFecha());
        holder.tvDescripcion.setText(evento.getDescripcion());

    }


    @Override
    public int getItemCount() {
        return listaEventos.size();
    }


    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvHora, tvFecha, tvDescripcion;


        /**
         * Constructor del ViewHolder: aquí es donde se hace findViewById
         * una sola vez por cada ViewHolder creado.
         *
         * @param itemView La vista completa del item (el LinearLayout completo)
         */
        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Buscar y guardar las referencias a cada TextView
            tvTitulo = itemView.findViewById(R.id.tv_titulo);
            tvHora = itemView.findViewById(R.id.tv_hora);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion);
        }
    }
}
