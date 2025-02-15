import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un joueur dans le jeu Troyes Dice.
 * Un joueur peut construire des bâtiments, collecter des ressources et marquer des points
 * en développant les trois branches de l'UTBM (EDIM, GMC, INFO).
 */
class Joueur {
    /** Pseudo du joueur */
    private String pseudo;
    /** Inventaire des ressources actuelles */
    private HashMap<Ressources, Integer> inventaireRes = new HashMap<>();
    /** Historique des ressources obtenues */
    private HashMap<Ressources, Integer> historiqueRes = new HashMap<>();
    /** Fiche individuelle du joueur */
    private Fiche fichIndiv;
    /** Nombre de bonus d'habitants obtenus */
    private int nbBonusHabObtenus = 0;
    /** Bonus de prestige pour la branche EDIM */
    private boolean bonusPrestigeBlanc = false;
    /** Bonus de prestige pour la branche INFO */
    private boolean bonusPrestigeJaune = false;
    /** Bonus de prestige pour la branche GMC */
    private boolean bonusPrestigeRouge = false;

    /**
     * Constructeur du joueur.
     * Initialise la fiche du joueur et lui donne ses ressources de départ.
     * @param pseudo Le nom du joueur
     */
    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.fichIndiv = new Fiche();
        for (Ressources ressource : Ressources.values()) {
            ajouterRessource(ressource, 3);
        }
    }

    /**
     * Calcule le score final du joueur.
     * Prend en compte les bâtiments construits, les habitants, les ressources restantes
     * et les bonus des cathédrales.
     * @return Le score total du joueur
     */
    public int calculerScore() {
        Fiche fiche = getFiche();

        int nb_Batiments_RC = 0;
        int nb_Batiments_RP = 0;

        int nb_Batiments_BC = 0;
        int nb_Batiments_BP = 0;

        int nb_Batiments_JC = 0;
        int nb_Batiments_JP = 0;

        int nb_hab = fiche.getNombreHab();

        int Or_restant = getInventaireRes().get(Ressources.ARGENT);
        int Connaissance_restante = getInventaireRes().get(Ressources.CONNAISSANCE);
        int Drapeaux_restants = getInventaireRes().get(Ressources.DRAPEAUX);

        int SCORE = 0;

        int rang = 0;
        for (Batiment batiment : fiche.getListeBatiments()) {
            if (batiment.isConstruit()){
                if (batiment.getCouleur() == Couleur.ROUGE && rang%2==0){
                    nb_Batiments_RC++;
                } else if (batiment.getCouleur() == Couleur.ROUGE && rang%2==1){
                    nb_Batiments_RP++;
                } else if (batiment.getCouleur() == Couleur.BLANC && rang%2==0){
                    nb_Batiments_BC++;
                } else if (batiment.getCouleur() == Couleur.BLANC && rang%2==1){
                    nb_Batiments_BP++;
                } else if (batiment.getCouleur() == Couleur.JAUNE && rang%2==0){
                    nb_Batiments_JC++;
                } else if (batiment.getCouleur() == Couleur.JAUNE && rang%2==1){
                    nb_Batiments_JP++;
                }
            }
            rang++;
        }

        for (int i = 0; i < 6; i++) {
            if (fiche.getListeCathedrales().get(i) ==0){
                if (i==0){
                    nb_Batiments_RP=0;
                } else if (i==1){
                    nb_Batiments_RC=0;
                } else if (i==2){
                    nb_Batiments_JP=0;
                } else if (i==3){
                    nb_Batiments_JC=0;
                } else if (i==4){
                    nb_Batiments_BP=0;
                } else if (i==5){
                    nb_Batiments_BC=0;
                }
            } else if (fiche.getListeCathedrales().get(i) ==3 || fiche.getListeCathedrales().get(i) ==4){
                if (i==0){
                    nb_Batiments_RP=nb_Batiments_RP*2;
                } else if (i==1){
                    nb_Batiments_RC=nb_Batiments_RC*2;
                } else if (i==2){
                    nb_Batiments_JP=nb_Batiments_JP*2;
                } else if (i==3){
                    nb_Batiments_JC=nb_Batiments_JC*2;
                } else if (i==4){
                    nb_Batiments_BP=nb_Batiments_BP*2;
                } else if (i==5){
                    nb_Batiments_BC=nb_Batiments_BC*2;
                }
            } else if (fiche.getListeCathedrales().get(i) ==5 || fiche.getListeCathedrales().get(i) ==6){   
                if (i==0){
                    nb_Batiments_RP=nb_Batiments_RP*3;
                } else if (i==1){
                    nb_Batiments_RC=nb_Batiments_RC*3;
                } else if (i==2){
                    nb_Batiments_JP=nb_Batiments_JP*3;
                } else if (i==3){
                    nb_Batiments_JC=nb_Batiments_JC*3;
                } else if (i==4){
                    nb_Batiments_BP=nb_Batiments_BP*3;
                } else if (i==5){
                    nb_Batiments_BC=nb_Batiments_BC*3;
                }
            }
        }
        System.out.println("CALCUL DU SCORE FINI");
        System.out.println("nb_Batiments_RC : " + nb_Batiments_RC);
        System.out.println("nb_Batiments_RP : " + nb_Batiments_RP);
        System.out.println("nb_Batiments_JC : " + nb_Batiments_JC);
        System.out.println("nb_Batiments_JP : " + nb_Batiments_JP);
        System.out.println("nb_Batiments_BC : " + nb_Batiments_BC);
        System.out.println("nb_Batiments_BP : " + nb_Batiments_BP);
        System.out.println("nb_hab : " + nb_hab);
        System.out.println("Or_restant : " + Or_restant +" "+ Or_restant/2);
        System.out.println("Connaissance_restante : " + Connaissance_restante +" "+ Connaissance_restante/2);
        System.out.println("Drapeaux_restants : " + Drapeaux_restants +" "+ Drapeaux_restants/2);

        SCORE = nb_Batiments_RC + nb_Batiments_RP + nb_Batiments_JC + nb_Batiments_JP + nb_Batiments_BC + nb_Batiments_BP + nb_hab + Or_restant/2 + Connaissance_restante/2 + Drapeaux_restants/2;
        System.out.println(("Grille finale :"));
        fiche.afficherFiche(this);
        System.out.println("Cathédrales :" + fiche.getListeCathedrales());
        System.out.println("Score : " + SCORE);

        return SCORE;
    }

    /**
     * Obtient le pseudo du joueur
     * @return Le pseudo du joueur
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Obtient la fiche du joueur
     * @return La fiche du joueur
     */
    public Fiche getFiche() {
        return fichIndiv;
    }

    /**
     * Obtient l'inventaire actuel des ressources du joueur
     * @return Une HashMap contenant les ressources et leurs quantités
     */
    public HashMap<Ressources, Integer> getInventaireRes() {
        return inventaireRes;
    }

    /**
     * Obtient l'historique des ressources obtenues par le joueur
     * @return Une HashMap contenant toutes les ressources obtenues
     */
    public HashMap<Ressources, Integer> getHistoriqueRes() {
        return historiqueRes;
    }

    /**
     * Obtient le nombre de bonus d'habitants déjà obtenus
     * @return Le nombre de bonus d'habitants
     */
    public int getNbBonusHabObtenus() {
        return nbBonusHabObtenus;
    }

    /**
     * Définit le nombre de bonus d'habitants obtenus
     * @param nbBonusHabObtenus Le nouveau nombre de bonus
     */
    public void setNbBonusHabObtenus(int nbBonusHabObtenus) {
        this.nbBonusHabObtenus = nbBonusHabObtenus;
    }

    /**
     * Ajoute des ressources à l'inventaire du joueur et met à jour l'historique.
     * Déclenche automatiquement les bonus d'habitants si les seuils sont atteints.
     * @param ressource Le type de ressource à ajouter
     * @param quantite La quantité à ajouter
     */
    public void ajouterRessource(Ressources ressource, int quantite) {
        System.out.println("Ajout de " + quantite + " " + ressource);
        inventaireRes.put(ressource, getInventaireRes().getOrDefault(ressource, 0) + quantite);
        if (getHistoriqueRes().getOrDefault(ressource, 0) < 6 && ((getHistoriqueRes().getOrDefault(ressource, 0) + quantite) >= 6)) {
            if (ressource == Ressources.DRAPEAUX) {
                getFiche().ajouterHab(Couleur.ROUGE, 1);
            }
            else if (ressource == Ressources.ARGENT) {
                getFiche().ajouterHab(Couleur.JAUNE, 1);
            }
            else if (ressource == Ressources.CONNAISSANCE) {
                getFiche().ajouterHab(Couleur.BLANC, 1);
            }
        }

        if (getHistoriqueRes().getOrDefault(ressource, 0) < 12 && ((getHistoriqueRes().getOrDefault(ressource, 0) + quantite) >= 12)) {
            if (ressource == Ressources.DRAPEAUX) {
                getFiche().ajouterHab(Couleur.ROUGE, 1);
            }
            else if (ressource == Ressources.ARGENT) {
                getFiche().ajouterHab(Couleur.JAUNE, 1);
            }
            else if (ressource == Ressources.CONNAISSANCE) {
                getFiche().ajouterHab(Couleur.BLANC, 1);
            }
        }

        if (getHistoriqueRes().getOrDefault(ressource, 0) < 18 && ((getHistoriqueRes().getOrDefault(ressource, 0) + quantite) >= 18)) {
            if (ressource == Ressources.DRAPEAUX) {
                getFiche().ajouterHab(Couleur.ROUGE, 1);
            }
            else if (ressource == Ressources.ARGENT) {
                getFiche().ajouterHab(Couleur.JAUNE, 1);
            }
            else if (ressource == Ressources.CONNAISSANCE) {
                getFiche().ajouterHab(Couleur.BLANC, 1);
            }
        }
        System.out.println("Inventaire de " + ressource + " : " + getInventaireRes().get(ressource));
        historiqueRes.put(ressource, getHistoriqueRes().getOrDefault(ressource, 0) + quantite);
    }

    /**
     * Retire des ressources de l'inventaire du joueur
     * @param ressource Le type de ressource à retirer
     * @param quantite La quantité à retirer
     */
    public void retirerRessource(Ressources ressource, int quantite) {
        inventaireRes.put(ressource, getInventaireRes().getOrDefault(ressource, 0) - quantite);
    }

    /**
     * Vérifie si le bonus de prestige EDIM est obtenu
     * @return true si le bonus est obtenu, false sinon
     */
    public boolean isBonusPrestigeBlanc() {
        return bonusPrestigeBlanc;
    }

    /**
     * Définit l'état du bonus de prestige EDIM
     * @param bonusPrestigeBlanc Le nouvel état du bonus
     */
    public void setBonusPrestigeBlanc(boolean bonusPrestigeBlanc) {
        this.bonusPrestigeBlanc = bonusPrestigeBlanc;
    }

    /**
     * Vérifie si le bonus de prestige INFO est obtenu
     * @return true si le bonus est obtenu, false sinon
     */
    public boolean isBonusPrestigeJaune() {
        return bonusPrestigeJaune;
    }

    /**
     * Définit l'état du bonus de prestige INFO
     * @param bonusPrestigeJaune Le nouvel état du bonus
     */
    public void setBonusPrestigeJaune(boolean bonusPrestigeJaune) {
        this.bonusPrestigeJaune = bonusPrestigeJaune;
    }

    /**
     * Vérifie si le bonus de prestige GMC est obtenu
     * @return true si le bonus est obtenu, false sinon
     */
    public boolean isBonusPrestigeRouge() {
        return bonusPrestigeRouge;
    }

    /**
     * Définit l'état du bonus de prestige GMC
     * @param bonusPrestigeRouge Le nouvel état du bonus
     */
    public void setBonusPrestigeRouge(boolean bonusPrestigeRouge) {
        this.bonusPrestigeRouge = bonusPrestigeRouge;
    }
}