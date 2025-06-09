package sistemaempresajdbc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioProyecto extends JFrame {
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtPresupuesto;
    private JComboBox<Departamento> comboDepartamentos;
    private JComboBox<String> comboEstado;
    private JTable tabla;
    private ProyectoDAO dao = new ProyectoDAO();
    private DepartamentoDAO daoDpto = new DepartamentoDAO();
    private List<Proyecto> listaProyectos;
    private int proyectoSeleccionadoId = -1;

    public FormularioProyecto() {
        setTitle("Gestión de Proyectos");
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
        JLabel lblNombre = new JLabel("Nombre del Proyecto:");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre = new JTextField(20);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        JLabel lblPresupuesto = new JLabel("Presupuesto:");
        lblPresupuesto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPresupuesto = new JTextField(10);

        JLabel lblDepartamento = new JLabel("Departamento:");
        lblDepartamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboDepartamentos = new JComboBox<>();
        cargarDepartamentos();

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboEstado = new JComboBox<>(new String[]{"PLANIFICADO", "EN_CURSO", "TERMINADO", "CANCELADO"});

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
        configurarEstiloInput(txtDescripcion);
        configurarEstiloInput(txtPresupuesto);
        configurarEstiloInput(comboDepartamentos);
        configurarEstiloInput(comboEstado);

        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String fecInicio = "2025-01-01";
                String fecTermino = "2025-12-31";
                double presupuesto = Double.parseDouble(txtPresupuesto.getText().trim());
                Departamento seleccionado = (Departamento) comboDepartamentos.getSelectedItem();
                String estado = (String) comboEstado.getSelectedItem();

                if (nombre.isEmpty() || seleccionado == null || estado == null) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
                    return;
                }

                Proyecto nuevo = new Proyecto(0, nombre, descripcion, fecInicio, fecTermino, seleccionado.getId(), presupuesto, estado, seleccionado.getNombre());
                dao.insertar(nuevo);
                limpiarCampos();
                mostrarProyectos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Presupuesto debe ser un número válido.");
            }
        });

        btnEditar.addActionListener(e -> {
            if (proyectoSeleccionadoId != -1) {
                try {
                    String nombre = txtNombre.getText().trim();
                    String descripcion = txtDescripcion.getText().trim();
                    // Fecha asignada externamente:
                    String fecInicio = "2025-01-01";
                    String fecTermino = "2025-12-31";
                    double presupuesto = Double.parseDouble(txtPresupuesto.getText().trim());
                    Departamento seleccionado = (Departamento) comboDepartamentos.getSelectedItem();
                    String estado = (String) comboEstado.getSelectedItem();

                    if (nombre.isEmpty() || seleccionado == null || estado == null) {
                        JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
                        return;
                    }

                    Proyecto actualizado = new Proyecto(proyectoSeleccionadoId, nombre, descripcion, fecInicio, fecTermino, seleccionado.getId(), presupuesto, estado, seleccionado.getNombre());
                    dao.actualizar(actualizado);
                    limpiarCampos();
                    mostrarProyectos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Presupuesto debe ser un número válido.");
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (proyectoSeleccionadoId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este proyecto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.eliminar(proyectoSeleccionadoId);
                    limpiarCampos();
                    mostrarProyectos();
                }
            }
        });

        btnCancelar.addActionListener(e -> limpiarCampos());

        // Layout form
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(lblNombre, gbc);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(lblDescripcion, gbc);
        gbc.gridx = 1; panelForm.add(scrollDescripcion, gbc);


        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(lblPresupuesto, gbc);
        gbc.gridx = 1; panelForm.add(txtPresupuesto, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(lblDepartamento, gbc);
        gbc.gridx = 1; panelForm.add(comboDepartamentos, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(lblEstado, gbc);
        gbc.gridx = 1; panelForm.add(comboEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(btnAgregar, gbc);
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

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                Proyecto p = listaProyectos.get(fila);
                proyectoSeleccionadoId = p.getIDProy();
                txtNombre.setText(p.getNombre());
                txtDescripcion.setText(p.getDescripcion());
                // No asignar fechas porque no hay inputs
                txtPresupuesto.setText(String.valueOf(p.getPresupuesto()));

                // Seleccionar departamento
                for (int i = 0; i < comboDepartamentos.getItemCount(); i++) {
                    if (comboDepartamentos.getItemAt(i).getNombre().equals(p.getNombreDepartamento())) {
                        comboDepartamentos.setSelectedIndex(i);
                        break;
                    }
                }

                // Seleccionar estado
                comboEstado.setSelectedItem(p.getEstado());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);

        panel.add(panelForm, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        add(panel);

        mostrarProyectos();
    }

    private void configurarEstiloInput(JComponent comp) {
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setBackground(Color.WHITE);
        comp.setForeground(Color.decode("#333333"));
        comp.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#2980B9"), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Si es JTextArea, añadir margen interno para que no quede pegado al borde
        if (comp instanceof JTextArea) {
            ((JTextArea) comp).setMargin(new Insets(5, 8, 5, 8));
        }

        // Para JComboBox, cambiar fondo y fuente del desplegable
        if (comp instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) comp;
            combo.setBackground(Color.WHITE);
            combo.setForeground(Color.decode("#333333"));
            combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            combo.setBorder(BorderFactory.createLineBorder(Color.decode("#2980B9"), 2));
            // También puedes agregar un renderer personalizado si quieres más control visual
        }
    }

    private void configurarBoton(JButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void mostrarProyectos() {
        listaProyectos = dao.obtenerTodos();
        String[][] data = new String[listaProyectos.size()][8];
        for (int i = 0; i < listaProyectos.size(); i++) {
            Proyecto p = listaProyectos.get(i);
            data[i][0] = String.valueOf(p.getIDProy());
            data[i][1] = p.getNombre();
            data[i][2] = p.getDescripcion();
            data[i][3] = p.getFec_Inicio() != null ? p.getFec_Inicio().toString() : "";
            data[i][4] = p.getFec_Termino() != null ? p.getFec_Termino().toString() : "";
            data[i][5] = p.getNombreDepartamento();
            data[i][6] = String.valueOf(p.getPresupuesto());
            data[i][7] = p.getEstado();
        }
        String[] columnas = {"ID", "Nombre", "Descripción", "Fecha Inicio", "Fecha Término", "Departamento", "Presupuesto", "Estado"};
        DefaultTableModel model = new DefaultTableModel(data, columnas) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla.setModel(model);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        // No hay inputs de fecha
        txtPresupuesto.setText("");
        comboDepartamentos.setSelectedIndex(-1);
        comboEstado.setSelectedIndex(0);
        proyectoSeleccionadoId = -1;
        tabla.clearSelection();
    }

    private void cargarDepartamentos() {
        List<Departamento> departamentos = daoDpto.obtenerTodos();
        DefaultComboBoxModel<Departamento> modelo = new DefaultComboBoxModel<>();
        for (Departamento d : departamentos) {
            modelo.addElement(d);
        }
        comboDepartamentos.setModel(modelo);
        comboDepartamentos.setSelectedIndex(-1);
    }
    
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing para evitar problemas de concurrencia
        javax.swing.SwingUtilities.invokeLater(() -> {
            FormularioProyecto formulario = new FormularioProyecto();
            formulario.setVisible(true);
        });
    }
}
