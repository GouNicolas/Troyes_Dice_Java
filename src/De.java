import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un dé dans le jeu.
 * Chaque dé a une valeur (1-6) et une couleur correspondant à une branche UTBM
 * ou une couleur spéciale (NOIR, TRANSPARENT).
 */
class De {
    /** Valeur du dé (1-6) */
    private int valeur;
    /** Couleur du dé */
    private CouleurDe couleur;

    /**
     * Lance le dé pour obtenir une valeur aléatoire entre 1 et 6
     */
    public void lancerDe() {
        valeur = (int) (Math.random() * 6) + 1;
    }

    /**
     * Définit la valeur du dé
     * @param valeur La nouvelle valeur du dé (1-6)
     */
    public void setVal(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Définit la couleur du dé
     * @param couleur La nouvelle couleur du dé
     */
    public void setCouleur(CouleurDe couleur) {
        this.couleur = couleur;
    }

    /**
     * Obtient la valeur actuelle du dé
     * @return La valeur du dé
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Obtient la couleur actuelle du dé
     * @return La couleur du dé
     */
    public CouleurDe getCouleur() {
        return couleur;
    }

    /**
     * Affiche les informations du dé (valeur et couleur)
     */
    public void afficherDe() {
        System.out.println("De : " + valeur + " (" + couleur + ")");
    }
}