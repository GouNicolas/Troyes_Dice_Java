import java.awt.Color;

enum Couleur {
    ROUGE,
    BLANC,
    JAUNE;

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