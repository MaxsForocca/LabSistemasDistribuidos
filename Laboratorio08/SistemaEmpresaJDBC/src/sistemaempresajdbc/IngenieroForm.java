package sistemaempresajdbc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IngenieroForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEspecialidad;
    private JTextField txtCargo;
    private JTextField txtSalario;
    private JTextField txtEmail;
    private JComboBox<Departamento> comboDepartamentos;
    private JTable tabla;
    private IngenieroDAO dao = new IngenieroDAO();
    private DepartamentoDAO daoDpto = new DepartamentoDAO();
    private List<Ingeniero> listaIngenieros;
    private int ingenieroSeleccionadoId = -1;
    private String fechaIngresoFija;

    public IngenieroForm(String fechaIngresoFija) {
        this.fechaIngresoFija = fechaIngresoFija;

        setTitle("Gestión de Ingenieros");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.decode("#F4F6F7"));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.decode("#F4F6F7"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre = new JTextField(20);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellido = new JTextField(20);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEspecialidad = new JTextField(20);

        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCargo = new JTextField(20);

        JLabel lblSalario = new JLabel("Salario:");
        lblSalario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSalario = new JTextField(10);

        JLabel lblDepartamento = new JLabel("Departamento:");
        lblDepartamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboDepartamentos = new JComboBox<>();
        cargarDepartamentos();

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail = new JTextField(20);

        JLabel lblFechaIngreso = new JLabel("Fecha Ingreso (fija): " + fechaIngresoFija);
        lblFechaIngreso.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblFechaIngreso.setForeground(Color.DARK_GRAY);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");

        configurarBoton(btnAgregar, new Color(41, 128, 185));
        configurarBoton(btnEditar, new Color(243, 156, 18));
        configurarBoton(btnEliminar, new Color(192, 57, 43));
        configurarBoton(btnCancelar, new Color(0, 0, 0));

        configurarEstiloInput(txtNombre);
        configurarEstiloInput(txtApellido);
        configurarEstiloInput(txtEspecialidad);
        configurarEstiloInput(txtCargo);
        configurarEstiloInput(txtSalario);
        configurarEstiloInput(txtEmail);
        configurarEstiloInput(comboDepartamentos);

        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String especialidad = txtEspecialidad.getText().trim();
                String cargo = txtCargo.getText().trim();
                String salarioStr = txtSalario.getText().trim();
                Departamento dpto = (Departamento) comboDepartamentos.getSelectedItem();
                String email = txtEmail.getText().trim();

                if (nombre.isEmpty() || apellido.isEmpty() || especialidad.isEmpty() || cargo.isEmpty() ||
                        salarioStr.isEmpty() || dpto == null || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
                    return;
                }

                double salario = Double.parseDouble(salarioStr);

                Ingeniero nuevo = new Ingeniero(0, nombre, apellido, especialidad, cargo, dpto.getId(),
                        salario, fechaIngresoFija, email);

                dao.insertar(nuevo);
                limpiarCampos();
                mostrarIngenieros();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Salario debe ser un número válido.");
            }
        });

        btnEditar.addActionListener(e -> {
            if (ingenieroSeleccionadoId != -1) {
                try {
                    String nombre = txtNombre.getText().trim();
                    String apellido = txtApellido.getText().trim();
                    String especialidad = txtEspecialidad.getText().trim();
                    String cargo = txtCargo.getText().trim();
                    String salarioStr = txtSalario.getText().trim();
                    Departamento dpto = (Departamento) comboDepartamentos.getSelectedItem();
                    String email = txtEmail.getText().trim();

                    if (nombre.isEmpty() || apellido.isEmpty() || especialidad.isEmpty() || cargo.isEmpty() ||
                            salarioStr.isEmpty() || dpto == null || email.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
                        return;
                    }

                    double salario = Double.parseDouble(salarioStr);

                    Ingeniero actualizado = new Ingeniero(ingenieroSeleccionadoId, nombre, apellido, especialidad, cargo,
                            dpto.getId(), salario, fechaIngresoFija, email);

                    dao.actualizar(actualizado);
                    limpiarCampos();
                    mostrarIngenieros();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Salario debe ser un número válido.");
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (ingenieroSeleccionadoId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este ingeniero?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.eliminar(ingenieroSeleccionadoId);
                    limpiarCampos();
                    mostrarIngenieros();
                }
            }
        });

        btnCancelar.addActionListener(e -> limpiarCampos());

        // Layout form
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(lblNombre, gbc);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(lblApellido, gbc);
        gbc.gridx = 1; panelForm.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(lblEspecialidad, gbc);
        gbc.gridx = 1; panelForm.add(txtEspecialidad, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(lblCargo, gbc);
        gbc.gridx = 1; panelForm.add(txtCargo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(lblSalario, gbc);
        gbc.gridx = 1; panelForm.add(txtSalario, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(lblDepartamento, gbc);
        gbc.gridx = 1; panelForm.add(comboDepartamentos, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panelForm.add(lblEmail, gbc);
        gbc.gridx = 1; panelForm.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panelForm.add(lblFechaIngreso, gbc);

        gbc.gridx = 0; gbc.gridy = 8; panelForm.add(btnAgregar, gbc);
        gbc.gridx = 1; panelForm.add(btnEditar, gbc);
        gbc.gridx = 2; panelForm.add(btnEliminar, gbc);
        gbc.gridx = 3; panelForm.add(btnCancelar, gbc);

        // Tabla
        tabla = new JTable();
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(93, 173, 226));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                Ingeniero ing = listaIngenieros.get(fila);
                cargarIngenieroSeleccionado(ing);
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);

        panel.add(panelForm, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        add(panel);

        mostrarIngenieros();
    }

    private void cargarIngenieroSeleccionado(Ingeniero ing) {
        ingenieroSeleccionadoId = ing.getIdIng();
        txtNombre.setText(ing.getNombre());
        txtApellido.setText(ing.getApellido());
        txtEspecialidad.setText(ing.getEspecialidad());
        txtCargo.setText(ing.getCargo());
        txtSalario.setText(String.valueOf(ing.getSalario()));
        txtEmail.setText(ing.getEmail());

        // Seleccionar departamento en el combo
        for (int i = 0; i < comboDepartamentos.getItemCount(); i++) {
            if (comboDepartamentos.getItemAt(i).getId() == ing.getIdDpto()) {
                comboDepartamentos.setSelectedIndex(i);
                break;
            }
        }
    }

    private void mostrarIngenieros() {
        listaIngenieros = dao.obtenerTodos();

        String[] columnas = {"ID", "Nombre", "Apellido", "Especialidad", "Cargo", "Departamento", "Salario", "Fecha Ingreso", "Email"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Ingeniero ing : listaIngenieros) {
            // Obtener nombre departamento del combo para mostrar
            String nombreDpto = "";
            for (int i = 0; i < comboDepartamentos.getItemCount(); i++) {
                Departamento d = comboDepartamentos.getItemAt(i);
                if (d.getId() == ing.getIdDpto()) {
                    nombreDpto = d.getNombre();
                    break;
                }
            }

            Object[] fila = {
                    ing.getIdIng(),
                    ing.getNombre(),
                    ing.getApellido(),
                    ing.getEspecialidad(),
                    ing.getCargo(),
                    nombreDpto,
                    ing.getSalario(),
                    ing.getFechaIngreso(),
                    ing.getEmail()
            };
            modelo.addRow(fila);
        }

        tabla.setModel(modelo);
    }

    private void limpiarCampos() {
        ingenieroSeleccionadoId = -1;
        txtNombre.setText("");
        txtApellido.setText("");
        txtEspecialidad.setText("");
        txtCargo.setText("");
        txtSalario.setText("");
        txtEmail.setText("");
        comboDepartamentos.setSelectedIndex(-1);
        tabla.clearSelection();
    }

    private void cargarDepartamentos() {
        comboDepartamentos.removeAllItems();
        List<Departamento> listaDptos = daoDpto.obtenerTodos();
        for (Departamento d : listaDptos) {
            comboDepartamentos.addItem(d);
        }
        comboDepartamentos.setSelectedIndex(-1);
    }

    private void configurarBoton(JButton btn, Color color) {
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void configurarEstiloInput(JComponent comp) {
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (comp instanceof JTextField) {
            ((JTextField) comp).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IngenieroForm formulario = new IngenieroForm("2025-01-01");
            formulario.setVisible(true);
        });
    }
}
