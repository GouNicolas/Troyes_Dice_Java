import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe représentant le plateau de jeu de Troyes Dice.
 * Gère les dés, les tuiles et les mécaniques de jeu principales.
 */
class Plateau {
    /** Liste des dés en jeu */
    private ArrayList<De> listesDes = new ArrayList<>();
    /** Liste des tuiles sur le plateau */
    private ArrayList<Tuile> listeTuiles = new ArrayList<>();
    /** Liste des coûts des actions */
    private ArrayList<Integer> listeCout = new ArrayList<>();
    /** Nombre maximum de tuiles sur le plateau */
    private static final int MAX_TUILES = 9;
    /** Nombre maximum de dés en jeu */
    private static final int MAX_DES = 4;

    /**
     * Constructeur du plateau.
     * Initialise les dés, les tuiles et les coûts.
     */
    public Plateau() {
        initialiserDes();
        initialiserTuiles();
        initialiserCouts();
    }

    /**
     * Lance tous les dés du plateau
     */
    public void lancerDe() {
        for (De de : listesDes) {
            de.lancerDe();
        }
    }

    /**
     * Modifie la valeur d'un dé
     * @param de Le dé à modifier
     * @param valeur La nouvelle valeur
     */
    public void modifierDe(De de, int valeur) {
        de.setVal(valeur);
    }

    /**
     * Obtient la liste des dés du plateau
     * @return La liste des dés
     */
    public ArrayList<De> getListesDes() {
        return listesDes;
    }

    /**
     * Obtient la liste des tuiles du plateau
     * @return La liste des tuiles
     */
    public ArrayList<Tuile> getListeTuiles() {
        return listeTuiles;
    }

    /**
     * Affiche l'état actuel du plateau
     * @param currentCycle Le cycle actuel (Jour/Nuit)
     * @param jour Le jour actuel
     */
    public void afficherPlateau(String currentCycle, int jour) {
        ModifierCouleurDeCouleurTuile(currentCycle, jour);
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

            for (int t=0; t < listesDes.size(); t++) {
                if (i == RangDetoRangTuile(currentCycle, jour, t)) {
                    System.out.println("De " + (t+1) + ": " + listesDes.get(t).getValeur() + " (" + listesDes.get(t).getCouleur() + ") " + showCout(t));
                }
            }
        }
        System.out.println("");
        System.out.println("===========================================================");
        System.out.printf("       Cycle actuel : %s %d                        \n", currentCycle, jour);
        System.out.println("===========================================================");
    }

    /**
     * Convertit la position d'un dé en position de tuile
     * @param currentCycle Le cycle actuel (Jour/Nuit)
     * @param jour Le jour actuel
     * @param rangDe Le rang du dé
     * @return La position de la tuile correspondante
     */
    public int RangDetoRangTuile(String currentCycle, int jour, int rangDe) {
        trierListeDes();
        int temp = 0;
        if (currentCycle.equals("Jour")) {
            temp = jour;
        } else {
            temp = jour + 4;
        }

        int rang = rangDe + temp;
        if (rang >= MAX_TUILES) {
            rang = rang - MAX_TUILES;
        }

        return rang;
    }

    public De DefromRangTuile(String currentCycle, int jour, int rangTuile) {
        for (int i = 0; i < listesDes.size(); i++) {
            if (RangDetoRangTuile(currentCycle, jour, i) == rangTuile) {
                return listesDes.get(i);
            }
        }
        return new De();
    }

    /**
     * Modifie la couleur des dés en fonction des tuiles
     * @param currentCycle Le cycle actuel (Jour/Nuit)
     * @param jour Le jour actuel
     */
    public void ModifierCouleurDeCouleurTuile(String currentCycle, int jour) {
        trierListeDes();

        for (int i = 0; i < listesDes.size(); i++) {
            if (listesDes.get(i).getCouleur() != CouleurDe.NOIR) {
                listesDes.get(i).setCouleur(CouleurDe.fromCouleur(this.listeTuiles.get(RangDetoRangTuile(currentCycle, jour, i)).getCouleur()));
            }
        }
    }

    /**
     * Initialise les tuiles du plateau aléatoirement
     */
    private void initialiserTuiles() {
        Random random = new Random();
        Couleur[] couleurs = Couleur.values();
        for (int i = 0; i < MAX_TUILES; i++) {
            Couleur couleur = couleurs[random.nextInt(couleurs.length)];
            Couleur couleurR = couleurs[random.nextInt(couleurs.length)];
            listeTuiles.add(new Tuile(couleur, couleurR));
        }
    }

    /**
     * Initialise les dés du plateau avec un dé noir aléatoire
     */
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

    /**
     * Trie les dés par valeur, avec le dé noir en priorité
     */
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

    /**
     * Vérifie si le joueur peut payer le coût d'une action
     * @param joueur Le joueur qui tente l'action
     * @param choix Le numéro de l'action choisie
     * @return 0 si possible, 1 si impossible, 2 pour anti-softlock
     */
    public int checkRessourcesAchat(Joueur joueur, int choix) {
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

    /**
     * Demande au joueur quelle ressource utiliser pour payer
     * @param joueur Le joueur qui doit payer
     * @return La ressource choisie
     */
    public Ressources choixPaiementChoix2(Joueur joueur) {
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

    /**
     * Affiche le coût d'une action
     * @param index L'index de l'action
     * @return Une chaîne décrivant le coût
     */
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

    /**
     * Initialise la liste des coûts des actions
     */
    public void initialiserCouts() {
        for (int i = 0; i < 4; i++) {
            listeCout.add(i);
        }
    }

    /**
     * Demande au joueur de choisir un dé
     * @return Le numéro du dé choisi
     */
    public int demanderChoixDe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir un dé (1-" + MAX_DES + "): ");
        int choix = scanner.nextInt();
        if (choix >= 1 && choix <= MAX_DES) {
            if (listesDes.get(choix - 1).getCouleur() == CouleurDe.NOIR) {
                System.out.println("Le dé choisi est noir. Veuillez choisir un autre dé.");
                return demanderChoixDe();
            }
            else {
                De deChoisi = listesDes.get(choix - 1);
                System.out.println("Vous avez choisi le dé " + choix + ": " + deChoisi.getValeur() + " (" + deChoisi.getCouleur() + ")");
                return choix;
            }
        } else {
            return demanderChoixDe();
        }
    }

    /**
     * Vérifie si la sélection d'un dé est valide
     * @param index L'index du dé
     * @return true si le dé peut être sélectionné
     */
    public boolean isDiceSelectionValid(int index) {
        if (index >= 0 && index < listesDes.size()) {
            return listesDes.get(index).getCouleur() != CouleurDe.NOIR;
        }
        return false;
    }

}