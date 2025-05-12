package LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Obtener la referencia al objeto remoto
            SistemaTarjetaCredito sistema = (SistemaTarjetaCredito) Naming
                    .lookup("rmi://localhost/SistemaTarjetaCredito");
            Scanner scanner = new Scanner(System.in);
            
            // SIMULACION DE TARJETA DE CREDITO
            // Datos iniciales de la tarjeta

            String card = "123456"; 
            String nombreTitular = "Llimy"; 
            // Validar tarjeta
            if (!sistema.validarTarjeta(card, nombreTitular)) {
                System.out.println("Tarjeta invalida");
                return;
            }

            System.err.println("Bienvenido " + nombreTitular);
            while (true) {
                System.out.print("-----------------|Sistema de Tarjetas de Credito|------------------\n1. Verificar saldo\n2. Realizar cargo\n3. Hacer reembolso\n4. Salir\n>Opcion: ");
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Saldo: $" + sistema.verificarSaldo(card));
                        break;
                    case 2:
                        System.out.print("Monto a cargar: ");
                        double monto = scanner.nextDouble();
                        System.out.println(sistema.cargarTarjeta(card, monto) ? "Cargo exitoso" : "Fondos insuficientes");
                        break;
                    case 3:
                        System.out.print("Monto a reembolsar: ");
                        double montoR = scanner.nextDouble();
                        System.out.println(sistema.reembolsarTarjeta(card, montoR) ? "Reembolso exitoso" : "Error");
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            }
        } catch (MalformedURLException murle) {
            System.out.println();
            System.out.println("MalformedURLException");
            System.out.println(murle);
        } catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        } catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        } catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
    }
}
