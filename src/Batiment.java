import java.util.ArrayList;
import java.util.HashMap;

abstract class Batiment {
    protected Couleur couleur;
    protected boolean construit;
    protected boolean detruit;

    public abstract void construire();
    public abstract void detruire();

    public Couleur getCouleur() {
        return couleur;
    }

    public boolean isConstruit() {
        return construit;
    }

    public boolean isDetruit() {
        return detruit;
    }
}