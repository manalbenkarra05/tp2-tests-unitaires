package org.example;

public class BanqueConcrète implements Banque {

    private int fond;
    private final int fondMin;

    public BanqueConcrète(int fondInitial, int fondMin) {
        this.fond = fondInitial;
        this.fondMin = fondMin;
    }

    @Override
    public void crediter(int somme) {
        fond += somme;
    }

    @Override
    public void debiter(int somme) {
        fond -= somme;
    }

    @Override
    public boolean est_solvable() {
        return fond >= fondMin;
    }

    public int getFond() {
        return fond;
    }
}
