package Clases_BD;
import java.awt.Component;
import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import Clases.*;

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
                usuarioEncontrado = u; 
                break; 
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
    }

    return usuarioEncontrado;
}

}