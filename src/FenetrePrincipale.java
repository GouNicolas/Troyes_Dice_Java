import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {
    private static final Dimension MIN_SIZE = new Dimension(800, 400);
    private static final Dimension FICHE_GUI_MIN_SIZE = new Dimension(300, 400); // Reduced minimum width
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 600); // More reasonable default size
    private PlateauGUI plateauGUI;
    private FicheGUI ficheGUIPanel;
    private Plateau plateau;
    private Partie partie;
    private BorderLayout mainLayout;

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
        mainPanel.add(new JLabel("Panel_principale"), BorderLayout.NORTH);

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

        // Add an empty column in the middle
        JPanel emptyPanel = new JPanel();
        gbc.gridx = 1;
        gbc.weightx = 0.4; // Ensure column 2 takes at least 40% of the width
        columnsPanel.add(emptyPanel, gbc);

        // Add FicheGUI to the third column
        this.ficheGUIPanel = ficheGUIPanel;
        ficheGUIPanel.setMinimumSize(FICHE_GUI_MIN_SIZE); // Set minimum size for FicheGUI
        gbc.gridx = 2;
        gbc.weightx = 0.6; // Ensure FicheGUI takes up to 60% of the width
        columnsPanel.add(ficheGUIPanel.getMainPanel(), gbc);

        mainPanel.add(columnsPanel, BorderLayout.CENTER);

        ChangementDeGUI changementDeGUI = new ChangementDeGUI(ficheGUIPanel);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Initialize window
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ensure all required objects are properly initialized
        Plateau plateau = new Plateau();
        Partie partie = new Partie();
        FicheController ficheController = new FicheController(new Fiche(), new Joueur("Joueur 1"));
        
        // Create and display the FenetrePrincipale frame
        FenetrePrincipale frame = new FenetrePrincipale(plateau, partie, ficheController, new FicheGUI(ficheController));
        frame.setVisible(true);
    }
}
