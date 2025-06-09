package sistemaempresajdbc;
import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/EmpresaProyectos?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "admin1"; // Tu contraseña de MySQL
    
    public static Connection getConnection() {
        try {
            // Cargar el driver (opcional en versiones nuevas, pero recomendado)
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            System.err.println("Verifica que el archivo JAR esté en Libraries del proyecto");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos");
            System.err.println("Verifica usuario, contraseña y que MySQL esté ejecutándose");
            e.printStackTrace();
            return null;
        }
    }
    
    public static void cerrarConexion(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexion cerrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
