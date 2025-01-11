/**
 * Énumération représentant les couleurs des branches de l'UTBM.
 * ROUGE pour GMC, BLANC pour EDIM, JAUNE pour INFO.
 */
import java.awt.Color;

enum Couleur {
    ROUGE,  // Représente la branche GMC
    BLANC,  // Représente la branche EDIM
    JAUNE;  // Représente la branche INFO

    /**
     * Convertit une CouleurDe en Couleur
     * @param couleurDe La couleur du dé à convertir
     * @return La couleur correspondante
     * @throws IllegalArgumentException si la couleur du dé n'est pas reconnue
     */
    public static Couleur fromCouleurDe(CouleurDe couleurDe) {
        switch (couleurDe) {
            case ROUGE:
                return ROUGE;
            case BLANC:
                return BLANC;
            case JAUNE:
                return JAUNE;
            default:
                throw new IllegalArgumentException("Unknown CouleurDe: " + couleurDe);
        }
    }

    public static Color convertCouleurDeToColor(CouleurDe couleurDe) {
        switch (couleurDe) {
            case ROUGE:
                return Color.RED;
            case JAUNE:
                return Color.YELLOW;
            case BLANC:
                return Color.WHITE;
            default:
                return Color.GRAY; // Default color if none match
        }
    }
}