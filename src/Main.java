import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Partie partie = new Partie();

        Joueur joueur1 = new Joueur("Alice");

        partie.ajouterJoueur(joueur1);

        partie.startGame();
    }
}