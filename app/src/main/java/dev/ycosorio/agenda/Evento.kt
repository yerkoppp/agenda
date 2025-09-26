package dev.ycosorio.agenda

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Evento(
    @JvmField var titulo: String?,
    @JvmField var fecha: String?,
    @JvmField var hora: String?,
    @JvmField var descripcion: String?
) : Parcelable
