import java.util.ArrayList;
import java.util.HashMap;

class BatimentPeon extends Batiment {
    public BatimentPeon(Couleur couleur) {
        this.couleur = couleur;
        this.construit = false;
        this.detruit = false;
    }

    @Override
    public void construire() {
        construit = true;
    }

    @Override
    public void detruire() {
        detruit = true;
    }
}