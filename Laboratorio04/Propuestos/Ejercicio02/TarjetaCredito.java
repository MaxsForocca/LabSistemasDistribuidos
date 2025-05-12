package LabSistemasDistribuidos.Laboratorio04.Propuestos.Ejercicio02;

public class TarjetaCredito implements TarjetaCreditoInterface {
    private String numeroTarjeta;
    private String nombreTitular;
    private double saldo;

    public TarjetaCredito(String numeroTarjeta, String nombreTitular) {
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.saldo = 100.0; // Inicializa el saldo a 100.0
    }

    @Override
    public void setSaldo(double newSaldo) {
        this.saldo = newSaldo;
    }

    @Override
    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    @Override
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public String getNombreTitular() {
        return nombreTitular;
    }

    @Override
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

}
