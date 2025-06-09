package sistemaempresajdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngenieroDAO {

    public List<Ingeniero> obtenerTodos() {
        List<Ingeniero> lista = new ArrayList<>();
        String sql = "SELECT i.IDIng, i.Nombre, i.Apellido, i.Especialidad, i.Cargo, i.IDDpto, i.Salario, i.Fecha_Ingreso, i.Email, " +
                     "d.Nombre AS nombreDepartamento " +
                     "FROM Ingenieros i " +
                     "INNER JOIN Departamentos d ON i.IDDpto = d.IDDpto";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("IDIng");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String especialidad = rs.getString("Especialidad");
                String cargo = rs.getString("Cargo");
                int idDpto = rs.getInt("IDDpto");
                double salario = rs.getDouble("Salario");
                String fechaIngreso = rs.getString("Fecha_Ingreso"); // puede ser null
                String email = rs.getString("Email");

                Ingeniero ing = new Ingeniero(id, nombre, apellido, especialidad, cargo, idDpto, salario, fechaIngreso, email);
                lista.add(ing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public void insertar(Ingeniero ing) {
        String sql = "INSERT INTO Ingenieros (Nombre, Apellido, Especialidad, Cargo, IDDpto, Salario, Fecha_Ingreso, Email) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ing.getNombre());
            stmt.setString(2, ing.getApellido());
            stmt.setString(3, ing.getEspecialidad());
            stmt.setString(4, ing.getCargo());
            stmt.setInt(5, ing.getIdDpto());
            stmt.setDouble(6, ing.getSalario());
            stmt.setString(7, ing.getFechaIngreso()); // aquí directo sin if
            stmt.setString(8, ing.getEmail());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void actualizar(Ingeniero ing) {
        String sql = "UPDATE Ingenieros SET Nombre = ?, Apellido = ?, Especialidad = ?, Cargo = ?, IDDpto = ?, Salario = ?, Fecha_Ingreso = ?, Email = ? " +
                     "WHERE IDIng = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ing.getNombre());
            stmt.setString(2, ing.getApellido());
            stmt.setString(3, ing.getEspecialidad());
            stmt.setString(4, ing.getCargo());
            stmt.setInt(5, ing.getIdDpto());
            stmt.setDouble(6, ing.getSalario());
            stmt.setString(7, ing.getFechaIngreso()); // aquí directo sin if
            stmt.setString(8, ing.getEmail());
            stmt.setInt(9, ing.getIdIng());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void eliminar(int id) {
        String sql = "DELETE FROM Ingenieros WHERE IDIng = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
