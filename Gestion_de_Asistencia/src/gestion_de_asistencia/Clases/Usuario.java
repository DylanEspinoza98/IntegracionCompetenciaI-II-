
package gestion_de_asistencia.Clases;

/**
 *
 * @author dolan
 */
public class Usuario {
    private int Id;
    private String Correo;
    private String Contrasena;
    private Rol rol;

    public Usuario() {
    }

    public int getId() { return Id; }

    public String getCorreo() { return Correo; }

    public String getContrasena() { return Contrasena; }

    public Rol getRol() { return rol; }

    public boolean validarLogin(String correo, String contrasena) {
        return this.Correo.equals(correo) && this.Contrasena.equals(contrasena);
    }
}
