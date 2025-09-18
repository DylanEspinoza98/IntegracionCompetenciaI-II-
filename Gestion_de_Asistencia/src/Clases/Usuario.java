
package Clases;

/**
 *
 * @author dolan
 */
public class Usuario {
    private int Id;
    private String Nombre;
    private String Correo;
    private String Contrasena;
    private int rol;

    public Usuario() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    
    public boolean validarLogin(String correo, String contrasena) {
        return this.Correo.equals(correo) && this.Contrasena.equals(contrasena);
    }
}
