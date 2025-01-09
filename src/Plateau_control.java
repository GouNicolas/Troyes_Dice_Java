import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Plateau_control {
    private Plateau plateau;
    private Partie partie;
    private PlateauGUI plateauGUI;

    public Plateau_control(Plateau plateau, Partie partie, PlateauGUI plateauGUI) {
        this.plateau = plateau;
        this.partie = partie;
        this.plateauGUI = plateauGUI;
        this.plateauGUI.setController(this);
    }

    public void handleTuileSelection(int tuileIndex) {
        // Implement the logic for handling tuile selection
        System.out.println("Tuile selected: " + tuileIndex);
        // Example: Update the model or perform actions based on the selected tuile
    }

    public void updateGUI() {
        plateauGUI.repaint();
    }

    // Example method to handle dice roll
    public void rollDice() {
        plateau.lancerDe();
        updateGUI();
    }

    // Example method to handle player turn
    public void playerTurn(Joueur joueur) {
        partie.tourDeJeu(joueur);
        updateGUI();
    }

    // Example method to handle passing the turn
    public void passTurn() {
        partie.passerTour();
        updateGUI();
    }

    // Example method to handle next turn
    public void nextTurn() {
        partie.prochainTour();
        updateGUI();
    }
    public int positionPremierDe() {
        // debug show
        System.out.println("nico"+plateau.RangDetoRangTuile(partie.currentCycle, partie.getJours(), 0));
        // Returns the position of the first die for the current day
        return plateau.RangDetoRangTuile(partie.currentCycle, partie.getJours(), 0);
    }
}