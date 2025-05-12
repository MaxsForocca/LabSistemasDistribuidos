package LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02;

/*  Ejecucion:
- Todo se hace en el directorio padre de /LabSistemasDistribuidos/
----TERMINAL 1---- compilar archivos .java y ejecutar rmiregistry

javac LabSistemasDistribuidos/Laboratorio04/Propuestos/Ejercicio02/*.java 

rmiregistry

----TERMINAL 2---- ejecutar el servidor

java LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02.Server

----TERMINAL 3---- ejecutar el cliente

java LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02.Cliente

*/
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class SistemaTarjetaCreditoImpl extends java.rmi.server.UnicastRemoteObject implements SistemaTarjetaCredito {
    private HashMap<String, TarjetaCredito> tarjetas; // <"numero tarjeta", TarjetaCredito>


    public SistemaTarjetaCreditoImpl() throws RemoteException {
        tarjetas = new HashMap<>();
    }
    public void agregarTarjeta(String numeroTarjeta, String nombreTitular) throws RemoteException {
        tarjetas.put(numeroTarjeta, new TarjetaCredito(numeroTarjeta, nombreTitular));
    }
    
    // Valida tarjeta 
    public boolean validarTarjeta(String numeroTarjeta, String nombreTitular) throws RemoteException {
        TarjetaCredito tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null && tarjeta.getNombreTitular().equals(nombreTitular)) {
            return true;
        }
        return false;
    }
    // Método para restar saldo a la tarjeta
    public boolean cargarTarjeta(String numeroTarjeta, double monto) throws RemoteException {
        TarjetaCredito tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null && monto > 0) {
            double saldoActual = tarjeta.getSaldo();
            if (saldoActual < monto) {
                return false; // Fondos insuficientes
            }
            tarjeta.setSaldo(saldoActual - monto);
            return true;
        }
        return false;
    }

    // Método para agregar saldo a la tarjeta 
    public boolean reembolsarTarjeta(String numeroTarjeta, double monto) throws RemoteException {
        TarjetaCredito tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null && monto > 0) {
            double saldoActual = tarjeta.getSaldo();
            tarjeta.setSaldo(saldoActual + monto);
            return true;
        }
        return false;
    }

    public double verificarSaldo(String numeroTarjeta) throws RemoteException {
        TarjetaCredito tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null) {
            return tarjeta.getSaldo();
        }
        return 0.0;
    }
}
