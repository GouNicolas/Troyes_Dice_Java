import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe abstraite représentant un bâtiment dans le jeu Troyes Dice.
 * Un bâtiment peut être de type prestige ou péon, et possède une couleur correspondant
 * à une branche de l'UTBM (EDIM, GMC, INFO).
 */
abstract class Batiment {
    /** La couleur du bâtiment (BLANC pour EDIM, ROUGE pour GMC, JAUNE pour INFO) */
    protected Couleur couleur;
    /** Indique si le bâtiment est construit */
    protected boolean construit;
    /** Indique si le bâtiment est détruit */
    protected boolean detruit;

    /**
     * Méthode abstraite pour construire le bâtiment
     */
    public abstract void construire();

    /**
     * Méthode abstraite pour détruire le bâtiment
     */
    public abstract void detruire();

    /**
     * Obtient la couleur du bâtiment
     * @return La couleur du bâtiment
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Vérifie si le bâtiment est construit
     * @return true si le bâtiment est construit, false sinon
     */
    public boolean isConstruit() {
        return construit;
    }

    /**
     * Vérifie si le bâtiment est détruit
     * @return true si le bâtiment est détruit, false sinon
     */
    public boolean isDetruit() {
        return detruit;
    }
}