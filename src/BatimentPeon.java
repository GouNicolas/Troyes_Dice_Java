import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un bâtiment péon dans le jeu.
 * Les bâtiments péons sont des bâtiments de base qui peuvent être construits
 * plus facilement que les bâtiments de prestige.
 */
class BatimentPeon extends Batiment {
    /**
     * Constructeur de BatimentPeon
     * @param couleur La couleur du bâtiment péon
     */
    public BatimentPeon(Couleur couleur) {
        this.couleur = couleur;
        this.construit = false;
        this.detruit = false;
    }

    /**
     * Construit le bâtiment péon
     */
    @Override
    public void construire() {
        construit = true;
    }

    /**
     * Détruit le bâtiment péon
     */
    @Override
    public void detruire() {
        detruit = true;
    }
}