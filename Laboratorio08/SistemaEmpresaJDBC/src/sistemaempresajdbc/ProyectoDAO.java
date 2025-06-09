package sistemaempresajdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class ProyectoDAO {

        public List<Proyecto> obtenerTodos() {
            List<Proyecto> lista = new ArrayList<>();
            String sql = "SELECT p.IDProy, p.Nombre, p.Descripcion, p.Fec_Inicio, p.Fec_Termino, p.IDDpto, p.Presupuesto, p.Estado, " +
                         "d.Nombre AS nombreDepartamento " +
                         "FROM Proyectos p " +
                         "INNER JOIN Departamentos d ON p.IDDpto = d.IDDpto";

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("IDProy");
                    String nombre = rs.getString("Nombre");
                    String descripcion = rs.getString("Descripcion");
                    String fecInicio = rs.getString("Fec_Inicio");
                    String fecTermino = rs.getString("Fec_Termino"); // puede ser null
                    int idDpto = rs.getInt("IDDpto");
                    double presupuesto = rs.getDouble("Presupuesto");
                    String estado = rs.getString("Estado");
                    String nombreDpto = rs.getString("nombreDepartamento");

                    Proyecto p = new Proyecto(id, nombre, descripcion, fecInicio, fecTermino, idDpto, presupuesto, estado, nombreDpto);
                    lista.add(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return lista;
        }


        public void insertar(Proyecto p) {
            String sql = "INSERT INTO Proyectos (Nombre, Descripcion, Fec_Inicio, Fec_Termino, IDDpto, Presupuesto, Estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, p.getNombre());
                stmt.setString(2, p.getDescripcion());
                stmt.setDate(3, java.sql.Date.valueOf(p.getFec_Inicio())); // formato: "YYYY-MM-DD"

                if (p.getFec_Termino() != null && !p.getFec_Termino().isEmpty()) {
                    stmt.setDate(4, java.sql.Date.valueOf(p.getFec_Termino()));
                } else {
                    stmt.setNull(4, java.sql.Types.DATE);
                }

                stmt.setInt(5, p.getIDDpto());
                stmt.setDouble(6, p.getPresupuesto());
                stmt.setString(7, p.getEstado());

                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public void actualizar(Proyecto p) {
            String sql = "UPDATE Proyectos SET Nombre = ?, Descripcion = ?, Fec_Inicio = ?, Fec_Termino = ?, IDDpto = ?, Presupuesto = ?, Estado = ? WHERE IDProy = ?";
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, p.getNombre());
                stmt.setString(2, p.getDescripcion());
                stmt.setDate(3, java.sql.Date.valueOf(p.getFec_Inicio())); // asumiendo String 'YYYY-MM-DD'
                if (p.getFec_Termino() != null && !p.getFec_Termino().isEmpty()) {
                    stmt.setDate(4, java.sql.Date.valueOf(p.getFec_Termino()));
                } else {
                    stmt.setNull(4, java.sql.Types.DATE);
                }
                stmt.setInt(5, p.getIDDpto());
                stmt.setDouble(6, p.getPresupuesto());
                stmt.setString(7, p.getEstado());
                stmt.setInt(8, p.getIDProy());

                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public void eliminar(int id) {
        String sql = "DELETE FROM Proyectos WHERE IDProy = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
