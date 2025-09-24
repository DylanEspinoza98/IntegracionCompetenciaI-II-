package Clases;

import java.sql.Time;
import java.sql.Date;


public class Asistencia {
    private int id_asistencia;
    private String rut;
    private Time h_entrada;
    private Time h_salida;
    private Date fecha_actual;
    private int id_tipo_asistencia;
    private String justificacion;

    public Asistencia() {
    }

    public Asistencia(String rut, Time h_entrada, Time h_salida, Date fecha_actual, int id_tipo_asistencia, String justificacion) {
        this.rut = rut;
        this.h_entrada = h_entrada;
        this.h_salida = h_salida;
        this.fecha_actual = fecha_actual;
        this.id_tipo_asistencia = id_tipo_asistencia;
        this.justificacion = justificacion;
    }

 
    public int getId_asistencia() {
        return id_asistencia;
    }

    public void setId_asistencia(int id_asistencia) {
        this.id_asistencia = id_asistencia;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Time getH_entrada() {
        return h_entrada;
    }

    public void setH_entrada(Time h_entrada) {
        this.h_entrada = h_entrada;
    }

    public Time getH_salida() {
        return h_salida;
    }

    public void setH_salida(Time h_salida) {
        this.h_salida = h_salida;
    }

    public Date getFecha_actual() {
        return fecha_actual;
    }

    public void setFecha_actual(Date fecha_actual) {
        this.fecha_actual = fecha_actual;
    }

    public int getId_tipo_asistencia() {
        return id_tipo_asistencia;
    }

    public void setId_tipo_asistencia(int id_tipo_asistencia) {
        this.id_tipo_asistencia = id_tipo_asistencia;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}