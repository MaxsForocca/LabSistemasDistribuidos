package LabSistemasDistribuidos.Laboratorio01.Propuestos.Ejercicio02;

class CubbyHole {
    private int contents;
    private boolean available = false;

    public synchronized int get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available = false;
        notify(); // despierta al productor
        return contents;
    }

    public synchronized void put(int value) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        contents = value;
        available = true;
        notify(); // despierta al consumidor
    }
}

class Productor extends Thread {
    private CubbyHole cubbyhole;
    private int numero;

    public Productor(CubbyHole c, int numero) {
        cubbyhole = c;
        this.numero = numero;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            cubbyhole.put(i);
            System.out.println("Productor #" + this.numero + " pone: " + i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) {}
        }
    }
}

class Consumidor extends Thread {
    private CubbyHole cubbyhole;
    private int numero;

    public Consumidor(CubbyHole c, int numero) {
        cubbyhole = c;
        this.numero = numero;
    }

    public void run() {
        int value;
        for (int i = 0; i < 10; i++) {
            value = cubbyhole.get();
            System.out.println("Consumidor #" + this.numero + " obtiene: " + value);
        }
    }
}

public class ProductorConsumidorModificado {
    public static void main(String[] args) {
        CubbyHole cubbyhole = new CubbyHole();
        Productor p = new Productor(cubbyhole, 1);
        Consumidor c = new Consumidor(cubbyhole, 1);

        p.start();
        c.start();
    }
}

