package sistemaempresajdbc;

import java.sql.*;
import java.util.*;

public class DepartamentoDAO {

    public List<Departamento> obtenerTodos() {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT IDDpto, Nombre FROM Departamentos";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("IDDpto");
                String nombre = rs.getString("Nombre");
                lista.add(new Departamento(id, nombre));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
