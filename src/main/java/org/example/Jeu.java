package org.example;

public class Jeu {

    private Banque banque;
    private boolean ouvert = true;

    public Jeu(Banque banque) {
        this.banque = banque;
    }

    public boolean estOuvert() {
        return ouvert;
    }

    public void fermer() {
        ouvert = false;
    }

    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {
        if (!ouvert) {
            throw new JeuFermeException("Le jeu est fermé !");
        }

        int mise = joueur.mise();

        try {
            joueur.debiter(mise);
        } catch (DebitImpossibleException e) {
            return; // joueur insolvable → rien ne se passe
        }

        banque.crediter(mise);

        int sommeDes = de1.lancer() + de2.lancer();

        if (sommeDes == 7) {
            int gain = 2 * mise;
            joueur.crediter(gain);
            banque.debiter(gain);
        }

        if (!banque.est_solvable()) {
            fermer();
        }
    }
}

