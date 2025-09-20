package Clases_BD;
import Clases.Usuario;
import java.awt.Component;

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
            "SELECT u.id AS Id, u.nombre AS Nombre,u.apellido as Apellido ,u.correo AS Correo, " +
            "u.password AS Contrasena, u.id_rol AS Rol FROM usuarios u"
        );

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("Id"));
            u.setApellido(rs.getString("Apellido"));
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
    
    public void DAO_crearUsuario(Usuario U, Date FechaIngreso) {
        String Sql = "Insert into usuarios (rut, nombre, apellido, correo, password, id_rol, id_area_trabajo, activo, fecha_ingreso, telefono, direccion, fecha_creacion, fecha_modificacion) VALUES (?,?, ?, ?, ?, ?, null, ?, ?, ?, ?, Current_TimeStamp, null)";
                                           
        try (PreparedStatement ps = Con.prepareStatement(Sql);) {
            ps.setString(1, U.getRut());
            ps.setString(2, U.getNombre());
            ps.setString(3, U.getApellido());
            ps.setString(4, U.getCorreo());
            ps.setString(5, U.getContrasena());
            ps.setInt(6, U.getRol());
            ps.setBoolean(7, true);
            ps.setDate(8, FechaIngreso);
            ps.setString(9, U.getTelefono());
            ps.setString(10, U.getDireccion()); 
            
            ps.executeUpdate();
        
        } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}