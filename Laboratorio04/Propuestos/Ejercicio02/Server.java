package LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            // Crear una instancia del objeto remoto
            SistemaTarjetaCredito sistema = new SistemaTarjetaCreditoImpl();
            
            // Crear una tarjeta de crédito de ejemplo
            sistema.agregarTarjeta("123456", "Llimy");

            // Registrar el objeto remoto en el registro RMI
            Naming.rebind("rmi://localhost/SistemaTarjetaCredito", sistema);
            
            System.out.println("Servidor RMI listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
