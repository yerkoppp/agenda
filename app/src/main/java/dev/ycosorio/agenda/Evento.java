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

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
