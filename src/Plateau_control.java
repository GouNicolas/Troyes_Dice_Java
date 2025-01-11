import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Plateau_control {
    private Plateau plateau;
    private Partie partie;
    private PlateauGUI plateauGUI;
    private boolean premireNuitpassee;
    private boolean changement;

    public Plateau_control(Plateau plateau, Partie partie, PlateauGUI plateauGUI) {
        this.plateau = plateau;
        this.partie = partie;
        this.plateauGUI = plateauGUI;
        this.plateauGUI.setController(this);
        this.premireNuitpassee = false;
        this.changement = true;
        updateGUI();
    }

    public void handleTuileSelection(int tuileIndex) {
        // Implement the logic for handling tuile selection
        tuileIndex = (tuileIndex+6)%9; // vrai index
        System.out.println("Tuile selected: " + tuileIndex);
        // Example: Update the model or perform actions based on the selected tuile
        plateauGUI.repaint(); // Repaint to update the view

        if (partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1) != null) {
            System.out.println("AAA" + partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1).getCouleur());
            System.out.println("AAA" + partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1).getValeur());
            System.out.println("B" + partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1));


            int rangeDe = 0;
            De de_temp = partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1);
            for (De de : partie.getPlateau().getListesDes()) {
                if (de == de_temp) {
                    break;
                }
                rangeDe++;
            }
            int verif = partie.getPlateau().checkRessourcesAchat(partie.getListeJoueurs().get(0), rangeDe);
            if (partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1).getCouleur() != CouleurDe.NOIR && verif == 0) {
                partie.getFenetrePrincipale().getChangementDeGUI().setDe(partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex-1));
            }
        }
    }

    public void updateGUI() {
        synchronizeRotation();
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
            plateauGUI.flipToNight();
        }
        updateGUI();
    }

    public int positionPremierDe() {
        // Returns the position of the first die for the current day
        return plateau.RangDetoRangTuile(partie.currentCycle, partie.getJours(), 0);
    }

    public void synchronizeRotation() {
        int totalCycles = (partie.getJours() - 1);
        double angle = totalCycles * 40;
        
        if (partie.currentCycle.equals("Nuit")) {
            premireNuitpassee = true;
        }
        if (partie.getJours() > 1 && premireNuitpassee && !changement) {
            plateauGUI.flipToNight();
            premireNuitpassee = !premireNuitpassee;
        }
        System.out.println("Angle: " + angle);
        plateauGUI.setTourAngle(angle);
        plateauGUI.repaint();
    }
}