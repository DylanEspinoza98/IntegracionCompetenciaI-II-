package gestion_de_asistencia.Clases;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author dolan
 */
public class Asistencia {
    private int id_Asistencia;
    private int id_Usuario;
    private LocalDate Fecha;
    private LocalTime HoraEntrada;
    private LocalTime HoraSalida;

    public Asistencia() {
        
    }
    
    public void marcarEntrada(Usuario User){
        
    };
    
    public void MarcarSalida(Usuario User){
        
    };
};

