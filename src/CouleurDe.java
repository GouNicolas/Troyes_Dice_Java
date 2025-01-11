/**
 * Énumération représentant les couleurs possibles des dés dans le jeu.
 * Inclut les couleurs des branches UTBM plus des couleurs spéciales (NOIR, TRANSPARENT).
 */
enum CouleurDe {
    ROUGE,      // Représente la branche GMC
    BLANC,      // Représente la branche EDIM
    JAUNE,      // Représente la branche INFO
    NOIR,       // Dé noir spécial
    TRANSPARENT; // Dé transparent spécial

    /**
     * Convertit une Couleur en CouleurDe
     * @param couleur La couleur à convertir
     * @return La couleur de dé correspondante
     * @throws IllegalArgumentException si la couleur n'est pas reconnue
     */
    public static CouleurDe fromCouleur(Couleur couleur) {
        switch (couleur) {
            case ROUGE:
                return ROUGE;
            case BLANC:
                return BLANC;
            case JAUNE:
                return JAUNE;
            default:
                throw new IllegalArgumentException("Unknown Couleur: " + couleur);
        }
    }
}
