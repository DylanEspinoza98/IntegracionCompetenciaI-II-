
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

    //Setter
    
    public void setId(int Id) { this.Id = Id; }

    public void setNombre(String Nombre) { this.Nombre = Nombre; }
    
    public void setCorreo(String Correo) { this.Correo = Correo; }

    public void setContrasena(String Contrasena) { this.Contrasena = Contrasena; }

    public void setRol(int rol) { this.rol = rol; }

 
    //Getter 
    
    public String getNombre() { return Nombre; }
    
    public int getId() { return Id; }

    public String getCorreo() { return Correo; }

    public String getContrasena() { return Contrasena; }

    public int getRol() { return rol; }
    
    

    public boolean validarLogin(String correo, String contrasena) {
        if(this.Correo.equals(correo) && this.Contrasena.equals(contrasena)){
            return true;
        }else{
            return false;
        }
    }
}
