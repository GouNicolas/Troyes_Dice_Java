import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {
    private PlateauGUI plateauGUI;
    private Plateau plateau;
    private Partie partie;
    private BorderLayout mainLayout;

    public FenetrePrincipale(Plateau plateau, Partie partie) {
        super("Troyes Dice Game");
        this.plateau = plateau;
        this.partie = partie;
        
        // Setup window
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));  // Add minimum size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainLayout = new BorderLayout();
        setLayout(mainLayout);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Panel_principale"), BorderLayout.NORTH);

        // Create columns panel
        JPanel columnsPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                int size = Math.min(getWidth(), getHeight());
                return new Dimension(size, size);
            }
        };
        columnsPanel.setLayout(new GridLayout(1, 3));

        // Add PlateauGUI
        this.plateauGUI = new PlateauGUI(plateau, partie);
        columnsPanel.add(plateauGUI);

        // Add fiche panel
        JPanel fiche = new JPanel();
        fiche.add(new JLabel("Fiche"));
        columnsPanel.add(fiche);

        mainPanel.add(columnsPanel, BorderLayout.CENTER);

        // Add north panel with player info
        JPanel northPanel = new JPanel(new GridLayout(1, 3));
        northPanel.add(new JPanel()); // Empty panel for column 1
        
        northPanel.add(new JLabel("Joueur_infos", SwingConstants.CENTER)); // Column 2
        
        northPanel.add(new JPanel()); // Empty panel for column 3
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Add rotate button
        JButton rotateButton = new JButton("Rotate Tour");
        rotateButton.addActionListener(e -> rotateTour());
        mainPanel.add(rotateButton, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
        
        // Initialize window
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
