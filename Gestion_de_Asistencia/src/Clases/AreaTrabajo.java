/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Ralabast
 */
public class AreaTrabajo {
    private int id_areatrabajo;
    private String nombre;
    private String descripcion;
    private boolean activo;

    // Constructores
    public AreaTrabajo() {}

    public AreaTrabajo(int id_areatrabajo, String nombre, String descripcion, boolean activo) {
        this.id_areatrabajo = id_areatrabajo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId_areatrabajo() { return id_areatrabajo; }
    public void setId_areatrabajo(int id_areatrabajo) { this.id_areatrabajo = id_areatrabajo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Método toString para mostrar en el ComboBox
    @Override
    public String toString() {
    return this.nombre; // o el campo que quieras mostrar
}

    // Método equals para comparaciones
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AreaTrabajo that = (AreaTrabajo) obj;
        return id_areatrabajo == that.id_areatrabajo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id_areatrabajo);
    }
    
}

