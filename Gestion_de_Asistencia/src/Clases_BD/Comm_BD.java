package Clases_BD;
import Clases.Usuario;

//Import para Sql
import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author dolan
 */
public class Comm_BD {
    Connection Con;
    
    
    public Comm_BD() {
        Con = Conn_BD.getConnection();
    };
    
    //Verificar si la contraseña coincide con el correo ingresado
    public Usuario VerificacionUsuario(String correo, String contrasena) {
    Usuario usuarioEncontrado = null;

    try {
        Statement st = Con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT u.id_usuario AS Id,u.rut as Rut , u.nombre AS Nombre , u.apellido as Apellido ,u.correo AS Correo, " +
            "u.contrasena AS Contrasena, u.id_rol AS Rol FROM usuario u"
        );

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("Id"));
            u.setRut(rs.getString("Rut"));
            u.setNombre(rs.getString("Nombre"));
            u.setApellido(rs.getString("Apellido"));
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
    
    public void DAO_crearUsuario(Usuario U) {
        String Sql = "Insert into usuario (rut, nombre, apellido, correo, contrasena, activo ,id_rol) Values (?,?,?,?,?,?,?);";
                                           
        try (PreparedStatement ps = Con.prepareStatement(Sql);) {
            ps.setString(1, U.getRut());
            ps.setString(2, U.getNombre());
            ps.setString(3, U.getApellido());
            ps.setString(4, U.getCorreo());
            ps.setString(5,U.getContrasena());
            ps.setBoolean(6, true);
            ps.setInt(7,U.getRol());
            
            
            ps.executeUpdate();
        
        } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Usuario ExtraerUsuario(String Rut){
        Usuario usuarioEncontrado = null;
        
        try {
            Statement st = Con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT u.id_usuario AS Id,u.rut as Rut , u.nombre AS Nombre , u.apellido as Apellido ,u.correo AS Correo, " +
                            "u.contrasena AS Contrasena, u.id_rol AS Rol FROM usuario u"
            );
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("Id"));
                u.setApellido(rs.getString("Apellido"));
                u.setNombre(rs.getString("Nombre"));
                u.setCorreo(rs.getString("Correo"));
                u.setContrasena(rs.getString("Contrasena"));
                u.setRol(rs.getInt("Rol"));
                
                if (u.getRut().equals(Rut)) {
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