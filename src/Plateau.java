import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class Plateau {
    private ArrayList<De> listesDes = new ArrayList<>();
    private ArrayList<Tuile> listeTuiles = new ArrayList<>();
    private ArrayList<Integer> listeCout = new ArrayList<>();
    private static final int MAX_TUILES = 9;
    private static final int MAX_DES = 4;

    public Plateau() {
        initialiserDes();
        initialiserTuiles();
        initialiserCouts();
    }

    public void lancerDe() {
        for (De de : listesDes) {
            de.lancerDe();
        }
    }

    public void modifierDe(De de, int valeur) {
        de.setVal(valeur);
    }

    public ArrayList<De> getListesDes() {
        return listesDes;
    }

    public ArrayList<Tuile> getListeTuiles() {
        return listeTuiles;
    }

    public void afficherPlateau(String currentCycle, int jour) {
        System.out.println("===========================================================");
        System.out.println("                     Plateau de jeu     ");
        System.out.println("===========================================================");
        System.out.println("                        Tuiles : ");
        int j = 0;
        int y = 0;
        trierListeDes();
        for (int i = 0; i < listeTuiles.size(); i++) {
            System.out.println("Tuile " + (i + 1) + ": " + listeTuiles.get(i).getCouleur() + " (" + listeTuiles.get(i).getCouleurRetourner() + ")");
            int temp = 0;
            if (currentCycle.equals("Jour")) {
                temp = jour;
            } else {
                temp = jour + 4;
            }

            if (temp + 3 >= MAX_TUILES && y < temp + 4 - MAX_TUILES) {
                y += 1;
                System.out.println("De " + (y) + ": " + listesDes.get(MAX_DES - y).getValeur() + " (" + listesDes.get(MAX_DES - y).getCouleur() + ") " + showCout(MAX_DES - y));
                
            }
            if ((i >= temp) && (j < listesDes.size())) {
                System.out.println("De " + (j + y + 1) + ": " + listesDes.get(j).getValeur() + " (" + listesDes.get(j).getCouleur() + ") "  + showCout(j));
                
                j += 1;
            }
        }
        System.out.println("");
        System.out.println("===========================================================");
        System.out.printf("       Cycle actuel : %s %d                        \n", currentCycle, jour);
        System.out.println("===========================================================");
    }

    private void initialiserTuiles() {
        Random random = new Random();
        Couleur[] couleurs = Couleur.values();
        for (int i = 0; i < MAX_TUILES; i++) {
            Couleur couleur = couleurs[random.nextInt(couleurs.length)];
            Couleur couleurR = couleurs[random.nextInt(couleurs.length)];
            listeTuiles.add(new Tuile(couleur, couleurR));
        }
    }

    private void initialiserDes() {
        Random random = new Random();
        int DE_NOIR = random.nextInt(MAX_DES);
        for (int i = 0; i < MAX_DES; i++) {
            De de = new De();
            de.lancerDe();
            if (i == DE_NOIR) {
                de.setCouleur(CouleurDe.NOIR);
            } else {
                de.setCouleur(CouleurDe.TRANSPARENT);
            }
            listesDes.add(de);
        }
    }

    private void trierListeDes() {
        listesDes.sort((de1, de2) -> {
            if (de1.getValeur() == de2.getValeur()) {
                if (de1.getCouleur() == CouleurDe.NOIR) {
                    return -1;
                } else if (de2.getCouleur() == CouleurDe.NOIR) {
                    return 1;
                }
            }
            return de1.getValeur() - de2.getValeur();
        });
    }

    public int checkRessourcesAchat(Joueur joueur, int choix){
        if (listesDes.get(0).getCouleur() == CouleurDe.NOIR && joueur.getInventaireRes().get(Ressources.ARGENT)==0 && joueur.getInventaireRes().get(Ressources.DRAPEAUX)==0 && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)==0) {
            System.out.println("Votre inventaire de ressources est vide et la case gratuite est occupee par le de noir. Application de la methode anti-softlock");
            return 2;
        } else if (choix == 2 && joueur.getInventaireRes().get(Ressources.ARGENT)==0 && joueur.getInventaireRes().get(Ressources.DRAPEAUX)==0 && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)==0) {
            System.out.println("Vous ne disposez pas des ressources necessaires. Meri de refaire un choix de de");
            return 1;                    
        } else if (choix == 3 && joueur.getInventaireRes().get(Ressources.ARGENT)==0) {
            System.out.println("Vous ne disposez pas des ressources necessaires. Meri de refaire un choix de de");
            return 1; 
        } else if (choix == 4 && joueur.getInventaireRes().get(Ressources.ARGENT)<=1) {
            System.out.println("Vous ne disposez pas des ressources necessaires. Meri de refaire un choix de de");
            return 1;
        }
        return 0;
    }

    public Ressources choixPaiementChoix2(Joueur joueur){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir la ressource a utiliser. 1 = Drapeaux, 2 = Argent et 3 = Connaissance");
        int typeRess = scanner.nextInt();
        if (typeRess == 1 && joueur.getInventaireRes().get(Ressources.DRAPEAUX)>=1) {
            return Ressources.DRAPEAUX;
        } else if (typeRess == 2 && joueur.getInventaireRes().get(Ressources.ARGENT)>=1) {
            return Ressources.ARGENT;
        } else if (typeRess == 3 && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)>=1) {
            return Ressources.CONNAISSANCE;
        } else {
            System.out.println("Ressource insuffisante pour l'achat. Merci de changer de choix");
            return choixPaiementChoix2(joueur);
        }


    }

    public String showCout(int index) {
        if (index < 0 || index >= listeCout.size()) {
            return "Invalid index";
        }
        int cout = listeCout.get(index);
        switch (cout) {
            case 0:
                return "Cout: 0";
            case 1:
                return "Cout: 1 de drapeaux, connaissance ou d'argent";
            case 2:
                return "Cout: 1 d'argent";
            case 3:
                return "Cout: 2 d'argent";
            default:
                return "Invalid cout";
        }
    }

    public void initialiserCouts() {
        for (int i = 0; i < 4; i++) {
            listeCout.add(i);
        }
    }

    public int demanderChoixDe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir un dé (1-" + MAX_DES + "): ");
        int choix = scanner.nextInt();
        if (choix >= 1 && choix <= MAX_DES && listesDes.get(choix - 1).getCouleur() != CouleurDe.NOIR) {
            De deChoisi = listesDes.get(choix - 1);
            System.out.println("Vous avez choisi le dé " + choix + ": " + deChoisi.getValeur() + " (" + deChoisi.getCouleur() + ")");
            demanderModifierDe(deChoisi);
            return choix;
        } else if (listesDes.get(choix - 1).getCouleur() == CouleurDe.NOIR) {
            System.out.println("Le dé choisi est noir. Veuillez choisir un autre dé.");
            return demanderChoixDe();
        } else {
            System.out.println("Choix invalide. Veuillez choisir un numéro entre 1 et " + MAX_DES + ".");
            return demanderChoixDe();
        }
    }

    public void demanderModifierDe(De de) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez-vous modifier un dé ? (O/N)");
        String choix = scanner.nextLine();
        if (choix.equalsIgnoreCase("O")) {
            System.out.println("Voulez-vous modifier la couleur (C) ou la valeur (V) ou annuler (N) ?");
            choix = scanner.nextLine();
            if (choix.equalsIgnoreCase("C")) {
                demanderModifierCouleur(de);
            } else if (choix.equalsIgnoreCase("V")) {
                demanderModifierValeur(de);
            } else if (choix.equalsIgnoreCase("N")) {
                demanderModifierDe(de);
            } else {
                System.out.println("Choix invalide. Veuillez choisir entre C, V et N.");
                demanderModifierDe(de);
            }
        }
    }

    private void demanderModifierCouleur(De de) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Modifier la couleur du De ? (B, J, R, N) ou anuler (A)");
        String couleur = scanner2.nextLine();
        if (couleur.equalsIgnoreCase("B")) {
            // Modifier la couleur du dé en blanc
            System.out.println("Couleur modifiée en Blanc");
        } else if (couleur.equalsIgnoreCase("J")) {
            // Modifier la couleur du dé en jaune
            System.out.println("Couleur modifiée en Jaune");
        } else if (couleur.equalsIgnoreCase("R")) {
            // Modifier la couleur du dé en rouge
            System.out.println("Couleur modifiée en Rouge");
        } else if (couleur.equalsIgnoreCase("N")) {
            // Modifier la couleur du dé en noir
            System.out.println("Couleur modifiée en Noir");
        } else if (couleur.equalsIgnoreCase("A")) {
            demanderModifierDe(de);
        } else {
            System.out.println("Couleur invalide. Veuillez choisir une couleur entre B, J, R et N.");
            demanderModifierCouleur(de);
        }
    }

    private void demanderModifierValeur(De de) {
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Modifier la valeur du De ? (1-6)");
        int valeur = scanner3.nextInt();
        if (valeur >= 1 && valeur <= 6 && valeur != de.getValeur()) {
            // Modifier la valeur du dé
            System.out.println("Valeur modifiée en " + valeur);
            modifierDe(de, valeur);
        } else if (valeur == de.getValeur()) {
            System.out.println("La valeur est déjà la même. Veuillez choisir une autre valeur.");
        } else {
            System.out.println("Valeur invalide. Veuillez choisir un numéro entre 1 et 6.");
            demanderModifierValeur(de);
        }
    }
}