package dev.ycosorio.agenda

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Evento(
    @JvmField var titulo: String?,
    @JvmField var fecha: LocalDate?,
    @JvmField var hora: LocalTime?,
    @JvmField var descripcion: String?
) : Parcelable
