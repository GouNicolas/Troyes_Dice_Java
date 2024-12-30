import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Partie {
    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    private Plateau plateau;
    private int jours;
    String currentCycle;

    public Partie() {
        this.plateau = new Plateau();
        this.jours = 1;
        this.currentCycle = "Jour";
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
                int choix = plateau.demanderChoixDe();

                while (plateau.checkRessourcesAchat(joueur, choix) == 1){
                    choix = plateau.demanderChoixDe();
                }
                if (plateau.checkRessourcesAchat(joueur, choix)==2){
                    joueur.ajouterRessource(Ressources.DRAPEAUX, 1);
                    joueur.ajouterRessource(Ressources.ARGENT, 1);
                    joueur.ajouterRessource(Ressources.CONNAISSANCE, 1);
                    prochainTour();
                } else {
                    if (choix == 2) {
                        Ressources paiement = plateau.choixPaiementChoix2(joueur);
                        joueur.retirerRessource(paiement, 1);
                    } else if (choix == 3){
                        joueur.retirerRessource(Ressources.ARGENT, 1);
                    } else if (choix == 4){
                        joueur.retirerRessource(Ressources.ARGENT, 2);
                    }
                    demanderModifierDe(joueur, plateau.getListesDes().get(choix - 1));
                    ConstruireChoix(choix, plateau, joueur);
                    prochainTour();
                }

            }
            try {
                Thread.sleep(1000); // 1 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    

    public void demanderModifierDe(Joueur joueur, De de) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez-vous modifier un dé ? (O/N)");
        String choix = scanner.nextLine();
        if (choix.equalsIgnoreCase("O")) {
            System.out.println("Voulez-vous modifier la couleur (C) ou la valeur (V) ou annuler (N) ?");
            choix = scanner.nextLine();
            if (choix.equalsIgnoreCase("C") && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)>0) {
                demanderModifierCouleur(joueur, de);
            } else if (choix.equalsIgnoreCase("V") && joueur.getInventaireRes().get(Ressources.DRAPEAUX)>0) {
                demanderModifierValeur(joueur, de);
            } else if (choix.equalsIgnoreCase("N")) {
                demanderModifierDe(joueur, de);
            } else {
                System.out.println("Choix invalide. Veuillez choisir entre C, V et N.");
                demanderModifierDe(joueur, de);
            }
        }
    }

    private void demanderModifierCouleur(Joueur joueur, De de) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Modifier la couleur du De ? (B, J, R) ou anuler (A)");
        String couleur = scanner2.nextLine();
        if (couleur.equalsIgnoreCase("B")) {
            // Modifier la couleur du dé en blanc
            System.out.println("Couleur modifiée en Blanc");
            de.setCouleur(CouleurDe.BLANC);
            joueur.retirerRessource(Ressources.CONNAISSANCE, 1);
        } else if (couleur.equalsIgnoreCase("J")) {
            // Modifier la couleur du dé en jaune
            System.out.println("Couleur modifiée en Jaune");
            de.setCouleur(CouleurDe.JAUNE);
            joueur.retirerRessource(Ressources.CONNAISSANCE, 1);
        } else if (couleur.equalsIgnoreCase("R")) {
            // Modifier la couleur du dé en rouge
            System.out.println("Couleur modifiée en Rouge");
            de.setCouleur(CouleurDe.ROUGE);
            joueur.retirerRessource(Ressources.CONNAISSANCE, 1);
        } else if (couleur.equalsIgnoreCase("A")) {
            demanderModifierDe(joueur, de);
        } else {
            System.out.println("Couleur invalide. Veuillez choisir une couleur entre B, J, R et N.");
            demanderModifierCouleur(joueur, de);
        }
        demanderModifierCouleur(joueur, de);
    }

    private void demanderModifierValeur(Joueur joueur, De de) {
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Modifier la valeur du De ? (1-6)");
        int valeur = scanner3.nextInt();
        if (valeur >= 1 && valeur <= 6 && valeur != de.getValeur()) {
            // Modifier la valeur du dé
            System.out.println("Valeur modifiée en " + valeur);
            de.setVal(valeur);
            joueur.retirerRessource(Ressources.DRAPEAUX, 1);
        } else if (valeur == de.getValeur()) {
            System.out.println("La valeur est déjà la même. Veuillez choisir une autre valeur.");
        } else {
            System.out.println("Valeur invalide. Veuillez choisir un numéro entre 1 et 6.");
            demanderModifierValeur(joueur, de);
        }
        demanderModifierDe(joueur, de);
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
