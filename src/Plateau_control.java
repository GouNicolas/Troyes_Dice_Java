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
        // si le joueur 
        int nombreTentatives = 0;
        // Implement the logic for handling tuile selection
        tuileIndex = (tuileIndex+6)%9; // vrai index
        System.out.println("Tuile selected: " + tuileIndex);
        System.out.println("AAA" + partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex - 1).getValeur() + " " + partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex - 1).getCouleur());

        De de_temp = partie.getPlateau().DefromRangTuile(partie.currentCycle, partie.getJours(), tuileIndex - 1);

        System.out.println("BBB" + de_temp.getValeur() + " " + de_temp.getCouleur());

        if (de_temp != null) {
            System.out.println("Tuile selected: " + tuileIndex);
            int rangeDe = 0;
            for (De de : partie.getPlateau().getListesDes()) {
                if (de == de_temp) {
                    break;
                }
                rangeDe++;
            }
            int verif = partie.getPlateau().checkRessourcesAchat(partie.getListeJoueurs().get(0), rangeDe);
            System.out.println(partie.getFenetrePrincipale().getChangementDeGUI().isLocked());
            if (de_temp.getCouleur() != CouleurDe.NOIR && verif == 0 && !partie.getFenetrePrincipale().getChangementDeGUI().isLocked()) {
                System.out.println("De selected: " + de_temp.getValeur() + " " + de_temp.getCouleur());
                partie.getFenetrePrincipale().getChangementDeGUI().setDe(de_temp);
                consommeRessources(rangeDe);
            } else {
                if (de_temp.getCouleur() == CouleurDe.NOIR) {
                    plateau.setErreur("Vous ne pouvez pas acheter un dé noir");
                } else if (verif == 1) {
                    plateau.setErreur("Vous n'avez pas assez de ressources pour acheter ce dé");
                } else {
                    handleAntiSoftlock();
                }
            }
        }
        updateGUI();
    }

    private void handleAntiSoftlock() {
        Joueur joueur = partie.getListeJoueurs().get(0);
        joueur.ajouterRessource(Ressources.DRAPEAUX, 1);
        joueur.ajouterRessource(Ressources.ARGENT, 1);
        joueur.ajouterRessource(Ressources.CONNAISSANCE, 1);
        partie.getFenetrePrincipale().getChangementDeGUI().setDe(null);
        // partie.prochainTour();
        updateGUI();
    }

    public void consommeRessources(int rangDe) {
        Joueur joueur = partie.getListeJoueurs().get(0);

        partie.getFenetrePrincipale().getChangementDeGUI().repaint();
        partie.getFenetrePrincipale().reload_fenetre(joueur);
        if (rangDe == 1) {
            plateauGUI.showResourceSelectionPopup();
        } else if (rangDe == 2) {
            joueur.retirerRessource(Ressources.ARGENT, 1);
        } else if (rangDe == 3) {
            joueur.retirerRessource(Ressources.ARGENT, 2);
        }
        partie.getFenetrePrincipale().reload_fenetre(joueur);
    }

    public void updateGUI() {
        synchronizeRotation();
        plateauGUI.updateCycleLabel(partie.currentCycle, partie.getJours());
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
        // partie.prochainTour();
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