import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
            Partie partie = new Partie();
            Joueur joueur1 = new Joueur("Alice");
            partie.ajouterJoueur(joueur1);
            
            // Create FenetrePrincipale before starting the game
            FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(partie.getPlateau(), partie);
            
            partie.startGame();
            System.out.println("Fin de la partie.");
            return;
    }
}