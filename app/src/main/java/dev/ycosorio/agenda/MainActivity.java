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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

import dev.ycosorio.agenda.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private EventoAdapter2 adapter;
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

        // Inicializar RecyclerView
        initRecyclerView();

        // Cargar datos
        cargarDatos();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaEventos = new ArrayList<>();
        adapter = new EventoAdapter2(listaEventos);
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

    private void cargarDatos() {
        // Agregar todos los estudiantes del bootcamp
        listaEventos.add(new Evento("Reunión", "10-10-2025", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Comprar supermercado", "29-09-2025", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Lavar Auto", "01-10-2025", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Hora médica", "15-11-2025", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Reunión en colegio", "25-11-2025", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Visita suegros", "12-10-2025", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Jugar a la pelota", "28-09-2025", "FutRate", "26-ago"));

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged();
    }



}