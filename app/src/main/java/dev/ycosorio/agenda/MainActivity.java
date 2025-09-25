package dev.ycosorio.agenda;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.ycosorio.agenda.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private EventoAdapter adapter;
    private List<Evento> listaEventos;
    private NavController navController; // Declara el NavController

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
        adapter = new EventoAdapter(listaEventos);
        recyclerView.setAdapter(adapter);
    }

    private void cargarDatos() {
        // Agregar todos los estudiantes del bootcamp
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));
        listaEventos.add(new Evento("Diego", "Alarcon", "FutRate", "26-ago"));

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged();
    }



}