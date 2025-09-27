package dev.ycosorio.agenda

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Evento(
    val id: String = GeneradorUnico().identificadorString, // Usando clase Java
    @JvmField var titulo: String?,
    @JvmField var fecha: LocalDate?,
    @JvmField var hora: LocalTime?,
    @JvmField var descripcion: String?
) : Parcelable {

    companion object {
        // Companion Object para manejo de creación de eventos
        fun crearEventoCompleto(
            titulo: String,
            fecha: LocalDate,
            hora: LocalTime,
            descripcion: String
        ): Evento {
            return Evento(
                titulo = titulo.takeIf { it.isNotBlank() },
                fecha = fecha,
                hora = hora,
                descripcion = descripcion.takeIf { it.isNotBlank() }
            )
        }

        fun crearEventoRapido(titulo: String): Evento {
            return Evento(
                titulo = titulo,
                fecha = LocalDate.now(),
                hora = LocalTime.now(),
                descripcion = null
            )
        }

        // Función para validar evento usando Kotlin Standard Functions
        fun validarEvento(evento: Evento): String {
            return evento.run {
                when {
                    titulo.isNullOrBlank() -> "Título requerido"
                    fecha == null -> "Fecha requerida"
                    hora == null -> "Hora requerida"
                    else -> "Evento válido"
                }
            }
        }
    }

    // Funciones de extensión para la clase Evento
    fun estaVencido(): Boolean = fecha?.isBefore(LocalDate.now()) ?: false

    fun obtenerResumen(): String {
        return this.let { evento ->
            evento.titulo?.let { titulo ->
                "Evento: $titulo"
            } ?: "Evento sin título"
        }.also { resumen ->
            println("Generando resumen: $resumen") // Uso de also para logging
        }
    }

    fun aplicarFormato(): Evento {
        return this.apply {
            // Uso de apply para modificar el objeto actual
            titulo = titulo?.trim()?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
            descripcion = descripcion?.trim()
        }
    }
}