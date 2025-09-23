package Clases_BD;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;

//Clase para Conectar el Programa con la BBDD

public class Conn_BD {
    public static String URL = "jdbc:mysql://localhost:3306/controlasistencia", Usuario= "root", Clave = "";
    
    public static Connection getConnection(){
        Connection Con = null;
        try{
            Con = DriverManager.getConnection(URL,Usuario,Clave);
        }catch(CommunicationsException e){
            JOptionPane.showMessageDialog(null, "INICIE EL SERVIDOR PARA CONTINUAR", "¡¡ERROR,NO HAY CONEXION CON BASE DE DATOS!! ", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }catch(SQLException ex){
            Logger.getLogger(Conn_BD.class.getName()).log(Level.SEVERE,null,ex);
            
        }
        return Con;
    }

    
}