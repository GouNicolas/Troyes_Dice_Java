/**
 * Énumération représentant les couleurs des branches de l'UTBM.
 * ROUGE pour GMC, BLANC pour EDIM, JAUNE pour INFO.
 */
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
}