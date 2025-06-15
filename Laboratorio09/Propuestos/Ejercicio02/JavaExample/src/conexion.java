import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    private static final String DB = "cuentasbancarias";
    private static final String USER = "usuario";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/"+DB;    

    /**
     * @return
     */
    public static Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Conexión exitosa a la base de datos " + DB);
            } else {
                System.out.println("No se pudo establecer la conexión a la base de datos " + DB);
            }
            return con;
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
        return null;
    }
}
