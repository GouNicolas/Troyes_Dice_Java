import java.util.ArrayList;
import java.util.HashMap;

class BatimentPrestige extends Batiment {
    public BatimentPrestige(Couleur couleur) {
        this.couleur = couleur;
        this.construit = false;
    }

    @Override
    public void construire() {
        construit = true;
    }
}