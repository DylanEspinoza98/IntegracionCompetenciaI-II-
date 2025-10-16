package Clases;


/**
 *
 * @author dolan
 */
import org.mindrot.jbcrypt.BCrypt;

public class Usuario {
    private int Id;
    private String Rut;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Contrasena;
    private int idrol;           // Mantengo el nombre original
    private int id_areatrabajo;  // NUEVO CAMPO AGREGADO

    // Constructor vacío
    public Usuario() {
    }
    
    // Constructor completo 
    public Usuario(String rut, String nombre, String apellido, String correo, 
                   String contrasena, int idrol, int id_areatrabajo) {
        this.Rut = rut;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Correo = correo;
        this.Contrasena = contrasena;
        this.idrol = idrol;
        this.id_areatrabajo = id_areatrabajo;
    }
    
    // Constructor sin área (para compatibilidad)
    public Usuario(String rut, String nombre, String apellido, String correo, 
                   String contrasena, int idrol) {
        this.Rut = rut;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Correo = correo;
        this.Contrasena = contrasena;
        this.idrol = idrol;
        this.id_areatrabajo = 0; // Sin área asignada por defecto
    }

    // Getters y Setters existentes
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getRut() {
        return Rut;
    }

    public void setRut(String Rut) {
        this.Rut = Rut;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasenaPlain) {
    this.Contrasena = contrasenaPlain;   // 
}

    // Rol - mantener compatibilidad con código existente
    public int getRol() {
        return idrol;
    }

    public void setRol(int idrol) {
        this.idrol = idrol;
    }
    
   
    public int getId_areatrabajo() {
        return id_areatrabajo;
    }

    public void setId_areatrabajo(int id_areatrabajo) {
        this.id_areatrabajo = id_areatrabajo;
    }
    
    // Método para obtener nombre completo
    public String getNombreCompleto() {
        return this.Nombre + " " + this.Apellido;
    }
    
    // Verificador para Validar LOGIN (existente)
   public boolean validarLogin(String correo, String contrasenaPlain) {
    if (!this.Correo.equals(correo)) {
        return false;
    }
    // Compara la contraseña ingresada con el hash almacenado
    return BCrypt.checkpw(contrasenaPlain, this.Contrasena);
}
    
    // Método toString para debugging
    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", Rut='" + Rut + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Rol=" + idrol +
                ", AreaTrabajo=" + id_areatrabajo +
                '}';
    }
}
