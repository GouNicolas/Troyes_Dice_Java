import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {
    private PlateauGUI plateauGUI;
    private Plateau plateau;
    private Partie partie;

    public FenetrePrincipale(Plateau plateau, Partie partie) {
        this.plateau = plateau;
        this.partie = partie;
        this.plateauGUI = new PlateauGUI(plateau, partie);
        
        setTitle("Troyes Dice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(plateauGUI, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void rotateTour() {
        plateauGUI.rotateTour();
    }

    public void refreshPlateau() {
        plateauGUI.repaint();
    }
}
