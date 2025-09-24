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
    
    //Extraer Usuario verificando si las contraseñas coinciden
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
    
    //Dao para crear nuevos usuarios
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
    
    //Dao para modificar los datos de un usuario existente
    public void Dao_ModificarUsuario(String dCambio, String Dato, String Rut){                                    
    try {
        Statement st = Con.createStatement();
        int filas = st.executeUpdate(
                " UPDATE usuario " +
                " SET " + dCambio + " = '" + Dato + "' " +
                " WHERE rut = '" + Rut + "';"
        );
        System.out.println("Filas actualizadas: " + filas);
    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    //Dao para un Soft delete de un usuario existente
    public void DAO_EliminarUsuario(String Rut){
    try {
        Statement st = Con.createStatement();
        int filas = st.executeUpdate(
            "UPDATE usuario " +
            "SET activo = false " +   // 👈 espacio extra aquí
            "WHERE rut = '" + Rut + "';"
        );
        System.out.println("Usuarios Desactivados: " + filas);
    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    //  Extraer usuario por su rut para su modificacion
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
                u.setRut(rs.getString("Rut"));
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
    
     //Registro entrada de asistencia
    public boolean registrarEntradaAsistencia(String rut) {
    String sql = "INSERT INTO Asistencia (rut, h_entrada, fecha_actual, id_tipo_asistencia) VALUES (?, ?, ?, ?)";

    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setString(1, rut);
        ps.setTime(2, new Time(System.currentTimeMillis()));
        ps.setDate(3, new Date(System.currentTimeMillis()));
        ps.setInt(4, 1); // 1 = Asistencia Normal

        int result = ps.executeUpdate();
        return result > 0;

    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}
    
    //Registro salida de asistencia
    public boolean registrarSalidaAsistencia(String rut) {
    String sql = "UPDATE Asistencia SET h_salida = ? WHERE rut = ? AND fecha_actual = ? AND h_salida IS NULL";

    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setTime(1, new Time(System.currentTimeMillis()));
        ps.setString(2, rut);
        ps.setDate(3, new Date(System.currentTimeMillis()));

        int result = ps.executeUpdate();
        return result > 0;

    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}
    
    //Verificar si ya existe registro de entrada para el día actual
    public boolean verificarEntradaHoy(String rut) {
    String sql = "SELECT COUNT(*) FROM Asistencia WHERE rut = ? AND fecha_actual = ?";

    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setString(1, rut);
        ps.setDate(2, new Date(System.currentTimeMillis()));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch(SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}
    
    // Verificar si el RUT existe en la base de datos
    public boolean verificarRutExiste(String rut) {
    String sql = "SELECT COUNT(*) FROM Usuario WHERE rut = ?";

    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setString(1, rut);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, "Error al verificar RUT: " + rut, ex);
    }
    return false;
}
    
    //Obtener nombre completo del usuario por RUT
    public String obtenerNombreUsuario(String rut) {
    String sql = "SELECT CONCAT(nombre, ' ', apellido) as nombre_completo FROM Usuario WHERE rut = ?";

    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setString(1, rut);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("nombre_completo");
        }

    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, "Error al obtener nombre para RUT: " + rut, ex);
    }
    return null;
}
    
    //Verificar si ya registró salida hoy
    public boolean verificarSalidaHoy(String rut) {
    String sql = "SELECT h_salida FROM Asistencia WHERE rut = ? AND fecha_actual = ?";
    
    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setString(1, rut);
        ps.setDate(2, new Date(System.currentTimeMillis()));
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getTime("h_salida") != null;
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, "Error al verificar salida para RUT: " + rut, ex);
    }
    return false;
}
    
    //Registro de las licencias
    public boolean registrarAusenciaLicencia(String rut, String motivoCompleto, 
                                       java.sql.Date fechaInicio, java.sql.Date fechaFin) {
    String sql = "INSERT INTO Licencia (rut, fecha_inicio, fecha_fin, motivo, estado) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement ps = Con.prepareStatement(sql)) {
        ps.setString(1, rut);                    // rut VARCHAR(12)
        ps.setDate(2, fechaInicio);             // fecha_inicio DATE
        ps.setDate(3, fechaFin);                // fecha_fin DATE
        ps.setString(4, motivoCompleto);        // motivo TEXT
        ps.setString(5, "PENDING");             // estado por defecto 'PENDING'
        
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
        
    } catch (SQLException ex) {
        Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE, 
            "Error al registrar licencia para RUT: " + rut, ex);
        return false;
    }
}
}
    
    
