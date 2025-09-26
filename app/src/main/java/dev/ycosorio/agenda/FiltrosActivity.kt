package dev.ycosorio.agenda

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ycosorio.agenda.databinding.ActivityFiltrosBinding

class FiltrosActivity : AppCompatActivity() {

    // Declara una variable para el binding
    private lateinit var binding: ActivityFiltrosBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentRecibido = intent
        val CLAVE_TAREAS = "CLAVE_TAREAS"

        val listaRecibida: ArrayList<Evento>? =
            intentRecibido.getParcelableArrayListExtra(CLAVE_TAREAS, Evento::class.java)

        // 2. Conexión limpia con View Binding
        listaRecibida?.let { lista ->

            // Accede al RecyclerView directamente a través del objeto binding
            binding.rvTareas.apply {
                // 1. Configurar LayoutManager
                layoutManager = LinearLayoutManager(this@FiltrosActivity)

                // 2. Conectar el adaptador
                adapter = EventoAdapter2(lista)
            }

            println("RecyclerView configurado con ${lista.size} tareas usando View Binding.")
        }
    }
}
