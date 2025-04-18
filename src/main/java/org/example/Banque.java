package org.example;

public interface Banque {
    void crediter(int somme);
    void debiter(int somme);
    boolean est_solvable();
}
