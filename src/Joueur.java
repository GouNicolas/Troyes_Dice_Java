import java.util.ArrayList;
import java.util.HashMap;

class Joueur {
    private String pseudo;
    private HashMap<Ressources, Integer> inventaireRes = new HashMap<>();
    private Fiche fichIndiv;
    private int nbBonusHabObtenus = 0;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.fichIndiv = new Fiche();
        for (Ressources ressource : Ressources.values()) {
            inventaireRes.put(ressource, 3);
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

    public int getNbBonusHabObtenus() {
        return nbBonusHabObtenus;
    }

    public void setNbBonusHabObtenus(int nbBonusHabObtenus) {
        this.nbBonusHabObtenus = nbBonusHabObtenus;
    }

    public void ajouterRessource(Ressources ressource, int quantite) {
        inventaireRes.put(ressource, getInventaireRes().getOrDefault(ressource, 0) + quantite);
    }

    public void retirerRessource(Ressources ressource, int quantite) {
        inventaireRes.put(ressource, getInventaireRes().getOrDefault(ressource, 0) - quantite);
    }
}