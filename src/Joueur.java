import java.util.ArrayList;
import java.util.HashMap;

class Joueur {
    private String pseudo;
    private HashMap<Ressources, Integer> inventaireRes = new HashMap<>();
    private HashMap<Ressources, Integer> historiqueRes = new HashMap<>();
    private Fiche fichIndiv;
    private int nbBonusHabObtenus = 0;
    private boolean bonusPrestigeBlanc = false;
    private boolean bonusPrestigeJaune = false;
    private boolean bonusPrestigeRouge = false;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.fichIndiv = new Fiche();
        for (Ressources ressource : Ressources.values()) {
            ajouterRessource(ressource, 3);
        }
    }

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

    public String getPseudo() {
        return pseudo;
    }

    public Fiche getFiche() {
        return fichIndiv;
    }

    public HashMap<Ressources, Integer> getInventaireRes() {
        return inventaireRes;
    }

    public HashMap<Ressources, Integer> getHistoriqueRes() {
        return historiqueRes;
    }

    public int getNbBonusHabObtenus() {
        return nbBonusHabObtenus;
    }

    public void setNbBonusHabObtenus(int nbBonusHabObtenus) {
        this.nbBonusHabObtenus = nbBonusHabObtenus;
    }

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

    public void retirerRessource(Ressources ressource, int quantite) {
        inventaireRes.put(ressource, getInventaireRes().getOrDefault(ressource, 0) - quantite);
    }

    public boolean isBonusPrestigeBlanc() {
        return bonusPrestigeBlanc;
    }

    public void setBonusPrestigeBlanc(boolean bonusPrestigeBlanc) {
        this.bonusPrestigeBlanc = bonusPrestigeBlanc;
    }

    public boolean isBonusPrestigeJaune() {
        return bonusPrestigeJaune;
    }

    public void setBonusPrestigeJaune(boolean bonusPrestigeJaune) {
        this.bonusPrestigeJaune = bonusPrestigeJaune;
    }

    public boolean isBonusPrestigeRouge() {
        return bonusPrestigeRouge;
    }

    public void setBonusPrestigeRouge(boolean bonusPrestigeRouge) {
        this.bonusPrestigeRouge = bonusPrestigeRouge;
    }
}