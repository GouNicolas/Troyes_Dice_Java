import java.util.ArrayList;
import java.util.HashMap;

class Tuile {
    private Couleur couleur;
    private Couleur couleurRetourner;

    public Tuile(Couleur couleur, Couleur couleurRetourner) {
        this.couleur = couleur;
        this.couleurRetourner = couleurRetourner;
    }

    public void retournerTuile() {
        this.couleur = this.couleurRetourner;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public Couleur getCouleurRetourner() {
        return couleurRetourner;
    }
}