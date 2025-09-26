package dev.ycosorio.agenda;

public class Evento {

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
    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

    public void setHora(String hora) {
        this.hora = hora;
    }
        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public String getDescripcion() {
        return descripcion;
    @Override
    public int describeContents() {
        return 0;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}
