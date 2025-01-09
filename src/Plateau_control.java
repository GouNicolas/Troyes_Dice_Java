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
        plateauGUI.repaint(); // Repaint to update the view
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
        String previousCycle = partie.currentCycle;
        partie.prochainTour();
        synchronizeRotation();
        if (!previousCycle.equals(partie.currentCycle) && partie.currentCycle.equals("Nuit")) {
            plateauGUI.flipTourImageHorizontally();
        }
        updateGUI();
    }
    public int positionPremierDe() {
        // Returns the position of the first die for the current day
        return plateau.RangDetoRangTuile(partie.currentCycle, partie.getJours(), 0);
    }

    public void synchronizeRotation() {
        int totalCycles = (partie.getJours() - 1);
        double angle = totalCycles * -40;

        if (partie.currentCycle.equals("Nuit")) {
            premireNuitpassee = true;
        }
        if (partie.getJours() > 1 && premireNuitpassee && !changement) {
            plateauGUI.flipTourImageHorizontally();
        }
        System.out.println("Angle: " + angle);
        plateauGUI.setTourAngle(angle);
        plateauGUI.repaint();
    }
}