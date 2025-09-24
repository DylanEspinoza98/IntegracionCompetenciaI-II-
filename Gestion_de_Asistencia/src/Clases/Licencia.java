package Clases;

import java.sql.Date;

public class Licencia {
    private int id_licencia;
    private String rut;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String motivo;
    private String estado; // PENDING, APPROVED, REJECTED

    public Licencia() {
    }

    public Licencia(String rut, Date fecha_inicio, Date fecha_fin, String motivo, String estado) {
        this.rut = rut;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.motivo = motivo;
        this.estado = estado;
    }

    public int getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(int id_licencia) {
        this.id_licencia = id_licencia;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}