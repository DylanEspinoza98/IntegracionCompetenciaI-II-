package Clases_BD;
import java.awt.Component;
import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author dolan
 */
public class Comm_BD {
    Connection Con;
    Component rootPane;
    
    
    public Comm_BD() {
        Con = Conn_BD.getConnection();
    };
    
    //Verificar el id del rol del usuario para permitir ingreso al sistema de registro
    public int GetId_RolUsuario(String Correo){
        int ID_rol = 0;
        try{
            Statement st = Con.createStatement();
            ResultSet rs = st.executeQuery("SELECT us.id_rol as 'Rol' FROM usuarios us WHERE us.correo = '" + Correo + "';");
            if(rs.next()){
                String parsearId = rs.getString("Rol");
                ID_rol = Integer.parseInt(parsearId);
                return ID_rol;
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE,null,ex);
        }
        return ID_rol;
    }
    
    //Verificar si la contraseña coincide con el correo ingresado
    
    public boolean VerificacionUsuarioContrasena (String Correo, String Contrasena){
      boolean verificador;
      try{
            Statement st = Con.createStatement();
            ResultSet rs = st.executeQuery("select correo, password from usuarios where correo = '" + Correo +"' and '"+Contrasena+"';");
            if(rs.next()){
                return verificador = true;
            }
        }catch(SQLException ex){
            Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE,null,ex);
        }
      return verificador = false;
    }
}