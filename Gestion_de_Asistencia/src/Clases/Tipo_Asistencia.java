package Clases;

public class Tipo_Asistencia {
    private int id_tipo_asistencia;
    private String nombre;
    private String descripcion;

    public Tipo_Asistencia() {
    }

    public Tipo_Asistencia(int id_tipo_asistencia, String nombre, String descripcion) {
        this.id_tipo_asistencia = id_tipo_asistencia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_tipo_asistencia() {
        return id_tipo_asistencia;
    }

    public void setId_tipo_asistencia(int id_tipo_asistencia) {
        this.id_tipo_asistencia = id_tipo_asistencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}