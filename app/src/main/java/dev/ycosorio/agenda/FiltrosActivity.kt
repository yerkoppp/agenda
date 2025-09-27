package dev.ycosorio.agenda

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.ycosorio.agenda.databinding.ActivityFiltrosBinding
import java.time.LocalDate
import java.util.*
import kotlin.collections.filter

class FiltrosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiltrosBinding
    private lateinit var eventosOriginales: ArrayList<Evento>
    private lateinit var eventosMostrados: ArrayList<Evento>
    private lateinit var eventoAdapter: EventoAdapter2

    companion object {
        const val CLAVE_TAREAS = "CLAVE_TAREAS"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtención de datos del intent
        eventosOriginales = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(CLAVE_TAREAS, Evento::class.java) ?: ArrayList()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra<Evento>(CLAVE_TAREAS) ?: ArrayList()
        }

        eventosMostrados = ArrayList(eventosOriginales)

        configurarRecyclerView()
        configurarDesplegables()
        mostrarContadorEventos()
    }

    private fun configurarRecyclerView() {
        eventoAdapter = EventoAdapter2(eventosMostrados)
        binding.rvTareas.apply {
            layoutManager = LinearLayoutManager(this@FiltrosActivity)
            adapter = eventoAdapter
        }
    }

    private fun configurarDesplegables() {
        val opcionesOrdenar = arrayOf(
            "Sin ordenar",
            "Fecha (Ascendente)",
            "Fecha (Descendente)",
            "Título (A-Z)",
            "Título (Z-A)"
        )
        val opcionesFiltrar = arrayOf(
            "Sin filtro",
            "Rango de Fechas",
            "Buscar por Título"
        )

        // Configurar adapters
        val adapterOrdenar = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcionesOrdenar)
        binding.actvOrdenar.setAdapter(adapterOrdenar)

        val adapterFiltrar = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcionesFiltrar)
        binding.actvFiltrar.setAdapter(adapterFiltrar)

        // Configurar listeners
        binding.actvOrdenar.setOnItemClickListener { _, _, position, _ ->
            aplicarOrdenamiento(opcionesOrdenar[position])
        }

        binding.actvFiltrar.setOnItemClickListener { _, _, position, _ ->
            when (opcionesFiltrar[position]) {
                "Sin filtro" -> quitarFiltros()
                "Rango de Fechas" -> mostrarDialogoRangoFechas()
                "Buscar por Título" -> mostrarDialogoBuscarTitulo()
            }
        }
    }

    private fun aplicarOrdenamiento(opcion: String) {
        val listaTemporal = when (opcion) {
            "Fecha (Ascendente)" -> eventosMostrados.sortedWith(compareBy(nullsLast()) { it.fecha })
            "Fecha (Descendente)" -> eventosMostrados.sortedWith(compareByDescending(nullsFirst()) { it.fecha })
            "Título (A-Z)" -> eventosMostrados.sortedWith(compareBy(nullsLast()) { it.titulo?.lowercase() })
            "Título (Z-A)" -> eventosMostrados.sortedWith(compareByDescending(nullsFirst()) { it.titulo?.lowercase() })
            else -> eventosMostrados.toList()
        }
        actualizarLista(listaTemporal)

        Snackbar.make(binding.root, "Ordenado por: $opcion", Snackbar.LENGTH_SHORT).show()
    }

    private fun mostrarDialogoRangoFechas() {
        // Usar fechas por defecto
        val hoy = LocalDate.now()
        var fechaInicio = hoy
        var fechaFin = hoy.plusDays(7)

        AlertDialog.Builder(this)
            .setTitle("Seleccionar Rango de Fechas")
            .setMessage("Selecciona el rango de fechas para filtrar los eventos")
            .setPositiveButton("Fecha Inicio") { _, _ ->
                mostrarDatePicker(fechaInicio) { nuevaFecha ->
                    fechaInicio = nuevaFecha
                    // Después de seleccionar fecha inicio, pedir fecha fin
                    mostrarDatePicker(fechaFin) { fechaFinSeleccionada ->
                        fechaFin = fechaFinSeleccionada
                        aplicarFiltroPorRangoDeFechas(fechaInicio, fechaFin)
                    }
                }
            }
            .setNeutralButton("Esta Semana") { _, _ ->
                aplicarFiltroPorRangoDeFechas(hoy, hoy.plusDays(7))
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDatePicker(fechaActual: LocalDate, onFechaSeleccionada: (LocalDate) -> Unit) {
        val calendar = Calendar.getInstance()
        calendar.set(fechaActual.year, fechaActual.monthValue - 1, fechaActual.dayOfMonth)

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val fechaSeleccionada = LocalDate.of(year, month + 1, dayOfMonth)
                onFechaSeleccionada(fechaSeleccionada)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun mostrarDialogoBuscarTitulo() {
        val editText = EditText(this)
        editText.hint = "Ingresa el texto a buscar..."

        AlertDialog.Builder(this)
            .setTitle("Buscar por Título")
            .setMessage("Ingresa el texto que quieres buscar en los títulos:")
            .setView(editText)
            .setPositiveButton("Buscar") { _, _ ->
                val texto = editText.text.toString().trim()
                if (texto.isNotEmpty()) {
                    aplicarFiltroPorTitulo(texto)
                } else {
                    Snackbar.make(binding.root, "Por favor ingresa un texto para buscar", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun quitarFiltros() {
        actualizarLista(eventosOriginales)
        Snackbar.make(binding.root, "Filtros eliminados", Snackbar.LENGTH_SHORT).show()
    }

    private fun aplicarFiltroPorRangoDeFechas(fechaInicio: LocalDate, fechaFin: LocalDate) {
        val eventosEnRango = eventosOriginales.filter { evento ->
            evento.fecha?.let { fecha ->
                fecha in fechaInicio..fechaFin
            } ?: false // Si fecha es null, no incluir en el rango
        }
        actualizarLista(eventosEnRango)

        Snackbar.make(
            binding.root,
            "Filtrado: ${eventosEnRango.size} eventos entre $fechaInicio y $fechaFin",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun aplicarFiltroPorTitulo(palabraClave: String) {
        val keywordLower = palabraClave.lowercase()
        val eventosConPalabra = eventosOriginales.filter { evento ->
            evento.titulo?.lowercase()?.contains(keywordLower) ?: false
        }
        actualizarLista(eventosConPalabra)

        Snackbar.make(
            binding.root,
            "Encontrados ${eventosConPalabra.size} eventos con: \"$palabraClave\"",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun actualizarLista(nuevaLista: List<Evento>) {
        eventosMostrados.clear()
        eventosMostrados.addAll(nuevaLista)
        eventoAdapter.notifyDataSetChanged()

        binding.tvDebugList.text = "Mostrando ${eventosMostrados.size} de ${eventosOriginales.size} eventos"
    }

    private fun mostrarContadorEventos() {
        binding.tvDebugList.text = "Total: ${eventosOriginales.size} eventos disponibles"
    }
}