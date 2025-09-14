package Clases.BD;
import java.awt.Component;
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
    
    public int GetIDUsuario(String Correo){
        int ID = 0;
        try{
            Statement st = Con.createStatement();
            ResultSet rs = st.executeQuery("SELECT us.id FROM usuarios us WHERE us.correo =" + Correo);
            if(rs.next()){
                String parsearId = rs.getString("ID");
                ID = Integer.parseInt(parsearId);
                return ID;
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Comm_BD.class.getName()).log(Level.SEVERE,null,ex);
        }
        return ID;
    }
    
}