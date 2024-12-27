import java.util.ArrayList;
import java.util.HashMap;

abstract class Batiment {
    protected Couleur couleur;
    protected boolean construit;

    public abstract void construire();

    public Couleur getCouleur() {
        return couleur;
    }

    public boolean isConstruit() {
        return construit;
    }
}
//OUIBON JOUR