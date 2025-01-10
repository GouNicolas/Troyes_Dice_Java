import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
            Partie partie = new Partie();
            Joueur joueur1 = new Joueur("Alice");
            partie.ajouterJoueur(joueur1);
            
            FicheController ficheController = new FicheController(new Fiche(), joueur1);
            FicheGUI ficheGUI = new FicheGUI(ficheController);
            
            // Create FenetrePrincipale before starting the game
            FenetrePrincipale fenetrePrincipale = new FenetrePrincipale(partie.getPlateau(), partie, ficheController, ficheGUI);
            
            partie.startGame(ficheController, ficheGUI);
            System.out.println("Fin de la partie.");
            return;
    }
}