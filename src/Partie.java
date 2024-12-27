import java.util.ArrayList;
import java.util.HashMap;

class Partie {
    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    private Plateau plateau;
    private int jours;
    String currentCycle;

    public Partie() {
        this.plateau = new Plateau();
        this.jours = 1;
        this.currentCycle = "Nuit";
    }

    public void ajouterJoueur(Joueur joueur) {
        listeJoueurs.add(joueur);
    }

    public void retirerJoueur(Joueur joueur) {
        listeJoueurs.remove(joueur);
    }

    public void tourDeJeu(Joueur joueur) {
        System.out.println("C'est le tour de " + joueur.getPseudo());
        joueur.getFiche().afficherFiche(joueur);
        plateau.afficherPlateau(currentCycle, jours);
    }

    public void passerTour() {
        // Passer le tour
    }

    public void prochainTour() {
        if (currentCycle.equals("Jour")) {
            currentCycle = "Nuit";
        } else {
            currentCycle = "Jour";
            jours += 1;
        }
    }

    public void startGame() {
        while (true) {
            for (Joueur joueur : listeJoueurs) {
                tourDeJeu(joueur);
                ConstruireChoix(plateau.demanderChoixDe(), plateau, joueur);
                prochainTour();
            }
            try {
                Thread.sleep(1000); // 1 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ConstruireChoix(int choix, Plateau plateau, Joueur joueur) {
        De deChoisi = plateau.getListesDes().get(choix - 1);
        System.out.println(choix + ": " + deChoisi.getValeur() + " (" + deChoisi.getCouleur() + ")");
        Couleur couleurDe = plateau.getListeTuiles().get(choix - 1).getCouleur();
        Fiche fiche = joueur.getFiche();

        for (Batiment batiment : fiche.getListeBatiments()) {
            if (batiment.getCouleur() == couleurDe && !batiment.isConstruit()) {
                batiment.construire();
                System.out.println("Batiment de couleur " + couleurDe + " construit." + choix);
                break;
            }
        }
    }

    public int TransformationChoixToIndex(int choix) {
        

        return 0;
    }
}
