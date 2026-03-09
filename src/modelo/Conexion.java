package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final String URL = "jdbc:mysql://localhost:3306/mydb";
    private final String USER = "root";
    private final String PASSWORD = "17494917";

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
           // System.out.println("Si se conecto");

        } catch (ClassNotFoundException | SQLException e)  {
            //System.out.println("No se encontró el driver de MySQL. ");
        }
        return con;
    }
}
