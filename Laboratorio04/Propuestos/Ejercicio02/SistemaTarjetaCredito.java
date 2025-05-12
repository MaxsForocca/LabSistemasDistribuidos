package LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface SistemaTarjetaCredito extends Remote {
    boolean validarTarjeta(String numeroTarjeta, String nombreTitular) throws RemoteException;
    void agregarTarjeta(String numeroTarjeta, String nombreTitular) throws RemoteException;
    boolean cargarTarjeta(String numeroTarjeta, double monto) throws RemoteException;
    boolean reembolsarTarjeta(String numeroTarjeta, double monto) throws RemoteException;
    double verificarSaldo(String numeroTarjeta) throws RemoteException;
}
