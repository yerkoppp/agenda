package dev.ycosorio.agenda;

import android.os.Parcel;
import android.os.Parcelable;

public class Evento implements Parcelable{

    private String titulo;
    private String fecha;
    private String hora;
    private String descripcion;

    public Evento(String titulo, String fecha, String hora, String descripcion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }


    protected Evento(Parcel in) {
        titulo = in.readString();
        fecha = in.readString();
        hora = in.readString();
        descripcion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(descripcion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }
        public void setDescripcion (String descripcion){
            this.descripcion = descripcion;

        }
    }