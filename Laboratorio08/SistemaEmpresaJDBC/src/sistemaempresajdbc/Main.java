package sistemaempresajdbc;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing para evitar problemas de concurrencia
        javax.swing.SwingUtilities.invokeLater(() -> {
            FormularioProyecto formulario = new FormularioProyecto();
            formulario.setVisible(true);
        });
    }
}
