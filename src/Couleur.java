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
}