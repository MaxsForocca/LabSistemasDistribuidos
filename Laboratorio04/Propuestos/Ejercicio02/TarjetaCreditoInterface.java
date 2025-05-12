package LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02;

public interface TarjetaCreditoInterface {
    void setSaldo(double newSaldo);
    void setNombreTitular(String nombreTitular);
    void setNumeroTarjeta(String numeroTarjeta);
    String getNombreTitular();
    String getNumeroTarjeta();
    double getSaldo();
}
