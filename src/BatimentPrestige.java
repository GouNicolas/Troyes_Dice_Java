import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un bâtiment de prestige dans le jeu.
 * Les bâtiments de prestige sont des bâtiments spéciaux qui offrent des bonus particuliers
 * et sont généralement plus difficiles à construire que les bâtiments péons.
 */
class BatimentPrestige extends Batiment {
    /**
     * Constructeur de BatimentPrestige
     * @param couleur La couleur du bâtiment de prestige
     */
    public BatimentPrestige(Couleur couleur) {
        this.couleur = couleur;
        this.construit = false;
        this.detruit = false;
    }

    /**
     * Construit le bâtiment de prestige
     */
    @Override
    public void construire() {
        construit = true;
    }

    /**
     * Détruit le bâtiment de prestige
     */
    @Override
    public void detruire() {
        detruit = true;
    }
}