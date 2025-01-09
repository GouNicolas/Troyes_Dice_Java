import javax.swing.*;
import java.awt.*;

public class GUIComplet extends JFrame {
    public GUIComplet(FicheController ficheController) {
        setTitle("GUI Complet");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the FicheGUI panel
        FicheGUI ficheGUIPanel = new FicheGUI(ficheController,null);

        // Create the ChangementDeGUI panel
        ChangementDeGUI changementDeGUIPanel = new ChangementDeGUI(ficheGUIPanel);


        // Add the ChangementDeGUI panel to the center
        add(changementDeGUIPanel, BorderLayout.CENTER);

        // Add the FicheGUI panel to the right
        add(ficheGUIPanel.getMainPanel(), BorderLayout.EAST);

    }

    public static void main(String[] args) {
        // Create a FicheController instance (assuming you have a constructor that takes no arguments)
        FicheController ficheController = new FicheController(new Fiche(), new Joueur("Joueur 1"));

        // Create and display the GUIComplet frame
        GUIComplet frame = new GUIComplet(ficheController);
        frame.setVisible(true);
    }
}