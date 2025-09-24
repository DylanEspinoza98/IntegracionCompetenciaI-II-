/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Ralabast
 */
public class Sesion_Usuario {
    private static Usuario usuarioActual = null;
    
    public static void setUsuario(Usuario usuario) {
        usuarioActual = usuario;
    }
    
    public static Usuario getUsuario() {
        return usuarioActual;
    }
    
    public static boolean hayUsuarioLoggeado() {
        return usuarioActual != null;
    }
    
    public static String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombre() : "Sin usuario";
    }
    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
    

