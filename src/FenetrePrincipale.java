import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {
    private static final Dimension MIN_SIZE = new Dimension(800, 400);
    private static final Dimension FICHE_GUI_MIN_SIZE = new Dimension(300, 400); // Reduced minimum width
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 600); // More reasonable default size
    private PlateauGUI plateauGUI;
    private FicheGUI ficheGUIPanel;
    private ChangementDeGUI changementDeGUI;
    private Plateau plateau;
    private Partie partie;
    private BorderLayout mainLayout;
    private JPanel scorePanel;

    public PlateauGUI getPlateauGUI() {
        return plateauGUI;
    }

    public FicheGUI getFicheGUIPanel() {
        return ficheGUIPanel;
    }

    public ChangementDeGUI getChangementDeGUI() {
        return changementDeGUI;
    }

    public FenetrePrincipale(Plateau plateau, Partie partie, FicheController ficheController, FicheGUI ficheGUIPanel) {
        super("Troyes Dice Game");
        this.plateau = plateau;
        this.partie = partie;
        
        // Setup window
        setSize(DEFAULT_SIZE);  // Set default size using variable
        setMinimumSize(MIN_SIZE);  // Set minimum size using variable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainLayout = new BorderLayout();
        setLayout(mainLayout);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create columns panel
        JPanel columnsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Add PlateauGUI to the first column
        this.plateauGUI = new PlateauGUI(plateau, partie);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // Ensure column 1 takes at least 40% of the width
        columnsPanel.add(plateauGUI, gbc);

        // Add ChangementDeGUI to the second column
        this.changementDeGUI = new ChangementDeGUI(ficheGUIPanel, partie.getListeJoueurs().get(0));
        gbc.gridx = 1;
        gbc.weightx = 0.2; // Ensure column 2 takes at least 20% of the width
        columnsPanel.add(changementDeGUI, gbc);

        // Add FicheGUI to the third column
        this.ficheGUIPanel = ficheGUIPanel;
        ficheGUIPanel.setMinimumSize(FICHE_GUI_MIN_SIZE); // Set minimum size for FicheGUI
        gbc.gridx = 2;
        gbc.weightx = 0.4; // Ensure FicheGUI takes up to 40% of the width
        columnsPanel.add(ficheGUIPanel.getMainPanel(), gbc);

        mainPanel.add(columnsPanel, BorderLayout.CENTER);

        // Create and setup the score panel
        scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        mainPanel.add(scorePanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
        
        // Initialize window
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void reload_fenetre(Joueur joueur) {
        if (this.ficheGUIPanel != null) {
            this.ficheGUIPanel.updateContent(joueur.getFiche(), joueur);
            this.ficheGUIPanel.reloadButtonStates();
            this.ficheGUIPanel.revalidate();
            this.ficheGUIPanel.repaint();
        } else {
            System.err.println("FicheGUI is null for " + joueur.getPseudo());
        }

        if (this.plateauGUI != null) {
            this.plateauGUI.refreshPlateau();
            this.plateauGUI.revalidate();
            this.plateauGUI.repaint();
        } else {
            System.err.println("PlateauGUI is null");
        }
    }

    // fonction pour mettre une popup d'erreur
    public void setErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}