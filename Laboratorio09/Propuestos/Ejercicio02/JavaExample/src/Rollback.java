import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class Rollback {
    public static void main(String[] args) {
        Connection connection = conexion.getConnection();
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;

        try {
            connection.setAutoCommit(false); // Desactivar autocommit para manejar transacciones manualmente

            // 1. Insertar banco
            stmt1 = connection.prepareStatement("INSERT INTO bancos (nombre, direccion) VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, "Banco Java");
            stmt1.setString(2, "Av. Central 789");
            stmt1.executeUpdate();
            System.out.println("1er INSERT en tabla 'bancos'");

            // Obtener el ID generado automaticamente
            ResultSet rs = stmt1.getGeneratedKeys();
            int bancoIdGenerado = -1;
            if (rs.next()) {
                bancoIdGenerado = rs.getInt(1);
                System.out.println("ID generado para el banco: " + bancoIdGenerado);
            } else {
                throw new SQLException("No se pudo obtener el ID del banco insertado.");
            }
            rs.close();

            // 2. Insertar titular
            stmt2 = connection.prepareStatement("INSERT INTO titulares (dni, nombre) VALUES (?,?);");
            stmt2.setString(1, "99998888");
            stmt2.setString(2, "Grupo3");
            stmt2.executeUpdate();
            System.out.println("2do INSERT en tabla 'titulares'");

            // 3. Insertar cuenta
            stmt3 = connection.prepareStatement("INSERT INTO cuentas (dni_titular, banco_id, saldo) VALUES (?, ?, ?);");
            stmt3.setString(1, "99998888");
            stmt3.setInt(2, bancoIdGenerado);
            stmt3.setDouble(3, 2500.0);
            stmt3.executeUpdate();
            System.out.println("3er INSERT en tabla 'cuentas'");

            // 4. Actualizar cuenta para decrementar saldo ERROR
            stmt4 = connection.prepareStatement("UPDATE cuentas SET saldo = saldo - ? WHERE id = ?");
            stmt4.setDouble(1, 100.0);
            stmt4.setInt(2, 999); // ID de cuenta inexistente 
            int filasAfectadas = stmt4.executeUpdate();
            System.out.println("4to UPDATE en tabla 'cuentas'");

            if (filasAfectadas == 0) {
                throw new SQLException("La cuenta a debitar no existe. Simulando error para rollback.");
            }

            // Si todo va bien
            connection.commit(); // confirmar transaccion
            System.out.println("Transaccion completada con exito.");

        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connection != null) {
                try {
                    System.out.println("Rollback realizado.");
                    connection.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer: " + ex1.getMessage());
                }
            }
        } finally {
            try {
                if (stmt1 != null) stmt1.close();
                if (stmt2 != null) stmt2.close();
                if (stmt3 != null) stmt3.close();
                if (stmt4 != null) stmt4.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        System.out.println("Conexion cerrada.");
    }
}
