enum CouleurDe {
    ROUGE,
    BLANC,
    JAUNE,
    NOIR,
    TRANSPARENT;

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
