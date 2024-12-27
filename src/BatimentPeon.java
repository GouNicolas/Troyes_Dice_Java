import java.util.ArrayList;
import java.util.HashMap;

class BatimentPeon extends Batiment {
    public BatimentPeon(Couleur couleur) {
        this.couleur = couleur;
        this.construit = false;
    }

    @Override
    public void construire() {
        construit = true;
    }
}