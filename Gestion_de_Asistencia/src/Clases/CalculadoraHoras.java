package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class CalculadoraHoras {

    private Time hEntrada;
    private Time hSalida;
    private Time hInicioJornada;
    private Time hFinJornada;

    public CalculadoraHoras(Time hEntrada, Time hSalida, Time hInicioJornada, Time hFinJornada) {
        this.hEntrada = hEntrada;
        this.hSalida = hSalida;
        this.hInicioJornada = hInicioJornada;
        this.hFinJornada = hFinJornada;
    }

    public double calcularHorasTrabajadas() {
    if (hEntrada == null || hSalida == null) return 0.0;
    return Duration.between(hEntrada.toLocalTime(), hSalida.toLocalTime()).toMinutes() / 60.0;
}

public double calcularHorasExtras() {
    if (hEntrada == null || hSalida == null) return 0.0;
    LocalTime salida = hSalida.toLocalTime();
    LocalTime finJornada = hFinJornada.toLocalTime();
    if (salida.isAfter(finJornada)) {
        return Duration.between(finJornada, salida).toMinutes() / 60.0;
    }
    return 0.0;
}

public long calcularAtraso() {
    if (hEntrada == null) return 0;
    LocalTime entrada = hEntrada.toLocalTime();
    LocalTime inicio = hInicioJornada.toLocalTime();
    if (entrada.isAfter(inicio)) {
        return Duration.between(inicio, entrada).toMinutes();
    }
    return 0;
}

}


