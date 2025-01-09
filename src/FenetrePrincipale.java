import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {
    private static final Dimension MIN_SIZE = new Dimension(800, 400);
    private PlateauGUI plateauGUI;
    private Plateau plateau;
    private Partie partie;
    private BorderLayout mainLayout;

    public FenetrePrincipale(Plateau plateau, Partie partie, FicheController ficheController) {
        super("Troyes Dice Game");
        this.plateau = plateau;
        this.partie = partie;
        
        // Setup window
        setSize(1200, 600);
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
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Add PlateauGUI to the first column
        this.plateauGUI = new PlateauGUI(plateau, partie);
        gbc.gridx = 0;
        gbc.gridy = 0;
        columnsPanel.add(plateauGUI, gbc);

        // Add an empty column in the middle
        JPanel emptyPanel = new JPanel();
        gbc.gridx = 1;
        columnsPanel.add(emptyPanel, gbc);

        // Add FicheGUI to the third column
        FicheGUI ficheGUIPanel = new FicheGUI(ficheController);
        gbc.gridx = 2;
        columnsPanel.add(ficheGUIPanel.getMainPanel(), gbc);

        mainPanel.add(columnsPanel, BorderLayout.CENTER);

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
        FenetrePrincipale frame = new FenetrePrincipale(plateau, partie, ficheController);
        frame.setVisible(true);
    }
}
