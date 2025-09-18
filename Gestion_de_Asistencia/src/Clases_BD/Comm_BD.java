package Clases_BD;
import Clases.Usuario;
import java.awt.Component;
import java.sql.*;
import java.util.ArrayList;
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
    
    public Usuario VerificacionUsuario(String correo, String contrasena) {
    Usuario usuarioEncontrado = null;

    try {
        Statement st = Con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT u.id AS Id, u.nombre AS Nombre, u.correo AS Correo, " +
            "u.password AS Contrasena, u.id_rol AS Rol FROM usuarios u"
        );

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("Id"));
            u.setNombre(rs.getString("Nombre"));
            u.setCorreo(rs.getString("Correo"));
            u.setContrasena(rs.getString("Contrasena"));
            u.setRol(rs.getInt("Rol"));

            if (u.validarLogin(correo, contrasena)) {
                usuarioEncontrado = u; // lo guardamos
                break; // dejamos de buscar
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
    }

    return usuarioEncontrado; // si es null, no existe
    }


}