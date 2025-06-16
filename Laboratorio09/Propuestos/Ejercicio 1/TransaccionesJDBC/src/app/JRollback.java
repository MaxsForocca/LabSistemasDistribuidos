package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JRollback {
    public static void main(String[] args) {
        Connection connection = Database.getConnection();
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;

        try {
            // 🔁 Deshabilitar autocommit
            connection.setAutoCommit(false);

            stmt1 = connection.prepareStatement("INSERT INTO mitabla VALUES (?, ?)");
            stmt2 = connection.prepareStatement("INSERT INTO miotratabla VALUES (?, ?, ?)");

            System.out.println("Primer INSERT en mitabla");
            stmt1.setString(1, "000004");
            stmt1.setString(2, "nuevo1@mail.com");
            stmt1.executeUpdate();

            System.out.println("Segundo INSERT en mitabla");
            stmt1.setString(1, "000005");
            stmt1.setString(2, "nuevo2@mail.com");
            stmt1.executeUpdate();

            System.out.println("Tercer INSERT en mitabla");
            stmt1.setString(1, "000006");
            stmt1.setString(2, "nuevo3@mail.com");
            stmt1.executeUpdate();

            System.out.println("INSERT en miotratabla con error");
            stmt2.setString(1, "Ana");
            stmt2.setString(2, "Gomez");
            stmt2.setString(3, "Hola soy un error"); // Este provoca el fallo
            stmt2.executeUpdate();

            // ✅ Si todo va bien, aplicar cambios
            connection.commit();

        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());

            // 🔁 Deshacer cambios si ocurre un error
            if (connection != null) {
                try {
                    System.out.println("Rollback");
                    connection.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo hacer rollback: " + ex1.getMessage());
                }
            }

        } finally {
            System.out.println("Cerrando conexión...");
            try {
                if (stmt1 != null) stmt1.close();
                if (stmt2 != null) stmt2.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
