package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionMySQL {

    public String db = "feedlot";
    // public String url = "jdbc:mysql://10.0.0.40:3306/" + db;
   // public String url = "jdbc:mysql://192.198.20.10:3306/" + db;
    //public String url = "jdbc:mysql://localhost:3306/" + db;
    public String url = "jdbc:mysql://localhost:3306/" + db;///modelo
    public String user = "root";
    //public String pass = "Cole978-+";
    public String pass = "root";

    public Connection Conectar() {
        Connection link = null;
        try {
            //Cargamos el Driver MySQL
            Class.forName("org.gjt.mm.mysql.Driver");
            //Creamos un enlace hacia la base de datos
            link = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor... Verifique su conexión a internet");
        }
        return link;
    }
}
