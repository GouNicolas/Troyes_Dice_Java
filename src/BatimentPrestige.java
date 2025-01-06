import java.util.ArrayList;
import java.util.HashMap;

class BatimentPrestige extends Batiment {
    public BatimentPrestige(Couleur couleur) {
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