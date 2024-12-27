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
        if (choix >= 1 && choix <= MAX_DES) {
            De deChoisi = listesDes.get(choix - 1);
            System.out.println("Vous avez choisi le dé " + choix + ": " + deChoisi.getValeur() + " (" + deChoisi.getCouleur() + ")");
            demanderModifierDe();
            return choix;
        } else {
            System.out.println("Choix invalide. Veuillez choisir un numéro entre 1 et " + MAX_DES + ".");
            return demanderChoixDe();
        }
    }

    public void demanderModifierDe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez-vous modifier un dé ? (O/N)");
        String choix = scanner.nextLine();
        if (choix.equalsIgnoreCase("O")) {
            demanderModifierCouleur();
            demanderModifierValeur();
        }
    }

    private void demanderModifierCouleur() {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Modifier la couleur du De ? (B, J, R, N)");
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
        } else {
            System.out.println("Couleur invalide. Veuillez choisir une couleur entre B, J, R et N.");
            demanderModifierCouleur();
        }
    }

    private void demanderModifierValeur() {
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Modifier la valeur du De ? (1-6)");
        int valeur = scanner3.nextInt();
        if (valeur >= 1 && valeur <= 6) {
            // Modifier la valeur du dé
        } else {
            System.out.println("Valeur invalide. Veuillez choisir un numéro entre 1 et 6.");
            demanderModifierValeur();
        }
    }
}