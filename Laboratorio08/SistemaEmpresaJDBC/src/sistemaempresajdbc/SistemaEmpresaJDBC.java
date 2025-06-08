package sistemaempresajdbc;
import java.sql.*;

public class SistemaEmpresaJDBC {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Empresa - JDBC");
        
        // Probar conexión
        Connection conn = ConexionBD.getConnection();
        
        if (conn != null) {
            try {
                // Consulta de prueba
                String sql = "SELECT d.Nombre as Departamento, COUNT(i.IDIng) as TotalIngenieros " +
                           "FROM Departamentos d " +
                           "LEFT JOIN Ingenieros i ON d.IDDpto = i.IDDpto " +
                           "GROUP BY d.IDDpto, d.Nombre";
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                
                System.out.println("\nRESUMEN DE DEPARTAMENTOS:");
                System.out.println("=====================================");
                while (rs.next()) {
                    String departamento = rs.getString("Departamento");
                    int totalIngenieros = rs.getInt("TotalIngenieros");
                    System.out.printf("%s: %d ingenieros\n", departamento, totalIngenieros);
                }
                
                rs.close();
                stmt.close();
                
            } catch (SQLException e) {
                System.err.println("Error en la consulta");
                e.printStackTrace();
            } finally {
                ConexionBD.cerrarConexion(conn);
            }
        }
    }
}