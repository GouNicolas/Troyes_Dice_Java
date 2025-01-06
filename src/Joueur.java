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
        // Implémenter le calcul de score ici
        return 0;
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