package acessoBD;
import java.sql.*;

/**
 *
 * @author manoel.neto
 */

public class ConexaoBD {
    public static Connection conector(){
        java.sql.Connection conecta = null;
        String driver = "com.mysql.jdbc.Driver";
        String caminho = "jdbc:mysql://localhost:3306/dbosso";
        String user = "root";
        String password = "";
        
        try {
            Class.forName(driver);
            conecta = DriverManager.getConnection(caminho, user, password);
            return conecta;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
}
