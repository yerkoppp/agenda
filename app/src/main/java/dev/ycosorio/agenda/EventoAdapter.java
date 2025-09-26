package dev.ycosorio.agenda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


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
        holder.tvTitulo.setText(evento.titulo);
        holder.tvHora.setText(evento.hora.toString());
        holder.tvDescripcion.setText(evento.descripcion);

        LocalDate fecha = evento.fecha;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu", Locale.getDefault());
        String fechaFormateada = fecha.format(formatter);

        holder.tvFecha.setText(fechaFormateada);
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
            tvHora = itemView.findViewById(R.id.tc_hora);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion);
        }
    }
}
