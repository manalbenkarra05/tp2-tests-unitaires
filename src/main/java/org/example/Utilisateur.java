package org.example;

public class Utilisateur {
    private String prenom;
    private String nom;
    private String email;
    private int id; // utilisé pour le scénario 3

    public Utilisateur(String prenom, String nom, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }

    // Pour le scénario 3
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}

