import java.util.ArrayList;
import java.util.HashMap;

class De {
    private int valeur;
    private CouleurDe couleur;

    public void lancerDe() {
        valeur = (int) (Math.random() * 6) + 1;
    }

    public void setVal(int valeur) {
        this.valeur = valeur;
    }

    public void setCouleur(CouleurDe couleur) {
        this.couleur = couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public CouleurDe getCouleur() {
        return couleur;
    }

    public void afficherDe() {
        System.out.println("De : " + valeur + " (" + couleur + ")");
    }
}