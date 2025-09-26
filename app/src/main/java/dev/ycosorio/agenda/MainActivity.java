package dev.ycosorio.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

import dev.ycosorio.agenda.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private EventoAdapter adapter;
    private ArrayList<Evento> listaEventos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        binding.btnGuardar.setOnClickListener(view -> {

            String titulo = binding.etTitulo.getText().toString();
            String sFecha = binding.etFecha.getText().toString();
            String sHora = binding.etHora.getText().toString();
            String descripcion = binding.etDescripcion.getText().toString();

            DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd-MM-uuuu", Locale.getDefault());
            DateTimeFormatter fmtHora  = DateTimeFormatter.ofPattern("HH:mm",      Locale.getDefault());

            LocalDate fecha;
            LocalTime hora;

            try {
                fecha = LocalDate.parse(sFecha, fmtFecha);
                hora  = LocalTime.parse(sHora,  fmtHora);
                // usar fecha y hora aquí
            } catch (DateTimeParseException e) {
                binding.etFecha.setError("Formato de fecha inválido (dd-MM-aaaa)");
                binding.etHora.setError("Formato de hora inválido (HH:mm)");
                return;
            }

            listaEventos.add(new Evento(titulo, fecha, hora, descripcion));

            adapter.notifyItemInserted(listaEventos.size() - 1);
            binding.recyclerViewTareas.scrollToPosition(listaEventos.size() - 1);
            // Notificar al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged();

            //Limpiar los editText
            binding.etTitulo.setText("");
            binding.etFecha.setText("");
            binding.etHora.setText("");
            binding.etDescripcion.setText("");
            view.clearFocus();

            Toast.makeText(MainActivity.this, "El evento se ha registrado.", Toast.LENGTH_SHORT).show();
        });

        // Inicializar RecyclerView
        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaEventos = new ArrayList<>();
        adapter = new EventoAdapter(listaEventos);
        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton botonFiltrar = binding.btnFiltrar;

        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FiltrosActivity.class);
                intent.putParcelableArrayListExtra("CLAVE_TAREAS", listaEventos);
                startActivity(intent);
            }

        });
    }

}