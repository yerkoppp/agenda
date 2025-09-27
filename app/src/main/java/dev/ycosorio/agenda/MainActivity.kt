package dev.ycosorio.agenda

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ycosorio.agenda.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EventoAdapter2
    private lateinit var listaEventos: ArrayList<Evento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        configurarListeners()
    }

    private fun configurarListeners() {
        // LAMBDA FUNCTION para botón guardar
        binding.btnGuardar.setOnClickListener { view ->
            val titulo = binding.etTitulo.text.toString()
            val sFecha = binding.etFecha.text.toString()
            val sHora = binding.etHora.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            // Usando Kotlin Standard Functions
            val resultado = validarYCrearEvento(titulo, sFecha, sHora, descripcion)
                .also { resultado ->
                    // ALSO: Se ejecuta siempre, útil para logging
                    println("Resultado de validación: $resultado")
                }

            when (resultado) {
                is ResultadoEvento.Exito -> {
                    resultado.evento.let { nuevoEvento ->
                        // LET: Ejecuta solo si evento no es null
                        listaEventos.add(nuevoEvento)
                        adapter.notifyItemInserted(listaEventos.size - 1)
                        binding.recyclerViewTareas.scrollToPosition(listaEventos.size - 1)

                        limpiarFormulario()
                        view.clearFocus()
                        Toast.makeText(this, "Evento registrado exitosamente", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultadoEvento.Error -> {
                    Toast.makeText(this, resultado.mensaje, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // ARROW FUNCTION para botón filtrar
        binding.btnFiltrar.setOnClickListener {
            Intent(this, FiltrosActivity::class.java).apply {
                // APPLY: Configura el Intent
                putParcelableArrayListExtra(FiltrosActivity.CLAVE_TAREAS, listaEventos)
            }.also { intent ->
                // ALSO: Ejecuta acción adicional
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView() {
        listaEventos = ArrayList()
        adapter = EventoAdapter2(listaEventos)

        // APPLY para configurar RecyclerView
        binding.recyclerViewTareas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun validarYCrearEvento(
        titulo: String,
        sFecha: String,
        sHora: String,
        descripcion: String
    ): ResultadoEvento {
        return run {
            // RUN: Ejecuta bloque de código y retorna el resultado
            if (titulo.trim().isEmpty()) {
                return@run ResultadoEvento.Error("El título es obligatorio")
            }

            val fmtFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
            val fmtHora = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

            try {
                val fecha = LocalDate.parse(sFecha, fmtFecha)
                val hora = LocalTime.parse(sHora, fmtHora)

                // Usando Companion Object de Evento
                val nuevoEvento = Evento.crearEventoCompleto(
                    titulo = titulo,
                    fecha = fecha,
                    hora = hora,
                    descripcion = descripcion
                )

                ResultadoEvento.Exito(nuevoEvento)
            } catch (e: DateTimeParseException) {
                with(binding) {
                    // WITH: Usar múltiples propiedades del objeto
                    etFecha.error = "Formato inválido (dd-MM-yyyy)"
                    etHora.error = "Formato inválido (HH:mm)"
                }
                ResultadoEvento.Error("Formato de fecha u hora inválido")
            }
        }
    }

    private fun limpiarFormulario() {
        // APPLY para limpiar todos los campos
        binding.apply {
            etTitulo.setText("")
            etFecha.setText("")
            etHora.setText("")
            etDescripcion.setText("")
        }
    }

    // Sealed class para manejo de resultados (característica de Kotlin)
    sealed class ResultadoEvento {
        data class Exito(val evento: Evento) : ResultadoEvento()
        data class Error(val mensaje: String) : ResultadoEvento()
    }
}