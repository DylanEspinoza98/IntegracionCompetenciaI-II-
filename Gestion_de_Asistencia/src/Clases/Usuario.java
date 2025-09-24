
package Clases;

/**
 *
 * @author dolan
 */
public class Usuario {
    private int Id;
    private String Rut;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Contrasena;
    private int Rol;

    public Usuario() {
    }

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

    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }

    private int idrol;
    
    public int getRol() {
    return idrol;
    }

    public void setRol(int idrol) {
    this.idrol = idrol;
    }
    
    
   //Verificador para Validar LOGIN
    
    public boolean validarLogin(String correo, String contrasena) {
        return this.Correo.equals(correo) && this.Contrasena.equals(contrasena);
    }
}
