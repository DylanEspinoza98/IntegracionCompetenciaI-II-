package Clases_BD;
import Clases.Usuario;
import Clases.Licencia;
import static Clases_BD.Conn_BD.getConnection;
//Import para Sql
import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

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
    // Obtener todas las solicitudes pendientes
    public List<Licencia> obtenerSolicitudesPendientes() {
    List<Licencia> solicitudes = new ArrayList<>();
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        Con = getConnection();
        if (Con != null) {
            String sql = "SELECT * FROM Licencia WHERE estado = 'PENDING' ORDER BY id_licencia DESC";
            pst = Con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Licencia licencia = new Licencia();
                licencia.setId_licencia(rs.getInt("id_licencia"));
                licencia.setRut(rs.getString("rut"));
                licencia.setFecha_inicio(rs.getDate("fecha_inicio"));
                licencia.setFecha_fin(rs.getDate("fecha_fin"));
                licencia.setMotivo(rs.getString("motivo"));
                licencia.setEstado(rs.getString("estado"));
                
                solicitudes.add(licencia);
            }
            
            System.out.println("Se encontraron " + solicitudes.size() + " solicitudes pendientes");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al obtener solicitudes pendientes: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (Con != null) Con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexiones: " + e.getMessage());
        }
    }
    
    return solicitudes;
}
    public boolean actualizarEstadoLicencia(int id, String estado) {
    Connection Con = null;
    PreparedStatement pst = null;
    boolean resultado = false;
    
    try {
        Con = getConnection();
        if (Con != null) {
            String sql = "UPDATE Licencia SET estado = ? WHERE id_licencia = ?";
            pst = Con.prepareStatement(sql);
            pst.setString(1, estado);
            pst.setInt(2, id);
            
            int filasAfectadas = pst.executeUpdate();
            
            if (filasAfectadas > 0) {
                resultado = true;
                System.out.println("Estado de licencia actualizado exitosamente. ID: " + id + ", Nuevo estado: " + estado);
            } else {
                System.out.println("No se pudo actualizar la licencia con ID: " + id);
            }
        }
        
    } catch (SQLException e) {
        System.out.println("Error al actualizar estado de licencia: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (pst != null) pst.close();
            if (Con != null) Con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexiones: " + e.getMessage());
        }
    }
    
    return resultado;
}
    public Licencia obtenerLicenciaPorId(int id) {
    Licencia licencia = null;
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        Con = getConnection();
        if (Con != null) {
            String sql = "SELECT * FROM Licencia WHERE id_licencia = ?";
            pst = Con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                licencia = new Licencia();
                licencia.setId_licencia(rs.getInt("id_licencia"));
                licencia.setRut(rs.getString("rut"));
                licencia.setFecha_inicio(rs.getDate("fecha_inicio"));
                licencia.setFecha_fin(rs.getDate("fecha_fin"));
                licencia.setMotivo(rs.getString("motivo"));
                licencia.setEstado(rs.getString("estado"));
                
                System.out.println("Licencia encontrada con ID: " + id);
            } else {
                System.out.println("No se encontró licencia con ID: " + id);
            }
        }
        
    } catch (SQLException e) {
        System.out.println("Error al obtener licencia por ID: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (Con != null) Con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexiones: " + e.getMessage());
        }
        
    }
    
    
    return licencia;
    
}
    //Obtener rol del usuario
    public int obtenerRolUsuario(String rut) {
    int rol = 3; // Por defecto trabajador
    Connection Con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        Con = getConnection();
        if (Con != null) {
            String sql = "SELECT idrol FROM Usuario WHERE rut = ?";
            pst = Con.prepareStatement(sql);
            pst.setString(1, rut);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                rol = rs.getInt("idrol");
                System.out.println("Rol encontrado para RUT " + rut + ": " + rol);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener rol: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (Con != null) Con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexiones: " + e.getMessage());
        }
    }
    
    return rol;
}







}
    
    
