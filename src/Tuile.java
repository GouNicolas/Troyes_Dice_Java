import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe Tuile représente une tuile carrée dont la couleur correspond à une branche de l'UTBM : EDIM, GMC, INFO.
 */
class Tuile {
    private Couleur couleur;
    private Couleur couleurRetourner;

    /**
     * Constructeur de la classe Tuile.
     * 
     * @param couleur La couleur initiale de la tuile.
     * @param couleurRetourner La couleur de la tuile après retournement.
     */
    public Tuile(Couleur couleur, Couleur couleurRetourner) {
        this.couleur = couleur;
        this.couleurRetourner = couleurRetourner;
    }

    /**
     * Retourne la tuile, changeant sa couleur à la couleur de retournement.
     */
    public void retournerTuile() {
        this.couleur = this.couleurRetourner;
    }

    /**
     * Obtient la couleur actuelle de la tuile.
     * 
     * @return La couleur actuelle de la tuile.
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Obtient la couleur de retournement de la tuile.
     * 
     * @return La couleur de retournement de la tuile.
     */
    public Couleur getCouleurRetourner() {
        return couleurRetourner;
    }
}