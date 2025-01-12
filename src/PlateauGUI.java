import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlateauGUI extends JPanel {
    private Plateau plateau;
    private Partie partie;
    private static final int PANEL_SIZE = 400;
    private static final int TUILE_SIZE = 80;
    private ArrayList<RoundRectangle2D> tuileRectangles;
    private int selectedTuileIndex = -1;
    
    // New fields for background images
    private static final String IMAGES_PATH = "src/ressources";
    private BufferedImage backgroundImage;
    private BufferedImage tourImage;
    private double tourAngle = 0; // Reset to 0 instead of 20
    private JLabel errorLabel;
    private Point tourCenter;
    private static final double TOUR_SCALE = 0.8; // 80% of panel size for tour
    private static final double BACKGROUND_SCALE = 1.0; // 100% of panel size for background
    private static final double NONAGON_SCALE = 0.9; // 90% of tour radius
    private static final double TUILE_SCALE = 0.45; // Increased from 0.35 to 0.45
    private Plateau_control controller;
    private JLabel cycleLabel;
    private static final Dimension MIN_SIZE = new Dimension(400, 400);
    private JPanel resourcesPanel;
    private static final String EDIM_IMAGE_PATH = IMAGES_PATH + "/Portage/EDIM/RessourceEdim.png";
    private static final String INFO_IMAGE_PATH = IMAGES_PATH + "/Portage/Info/RessourceInfo.png";
    private static final String GMC_IMAGE_PATH = IMAGES_PATH + "/Portage/GMC/RessourceGmc.png";
    private JPanel cyclePanel;

    public PlateauGUI(Plateau plateau, Partie partie) {
        this.plateau = plateau;
        this.partie = partie;
        this.tuileRectangles = new ArrayList<>();
        this.controller = new Plateau_control(plateau, partie, this);
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setMinimumSize(MIN_SIZE); // Set minimum size
        
        // Initialize error label
        errorLabel = new JLabel("Image could not be loaded", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        add(errorLabel);

        // Initialize cycle panel
        cyclePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add margin to the panel

        // Initialize cycle label
        cycleLabel = new JLabel("Jour - Tour 1", SwingConstants.CENTER);
        cycleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Match the style of "Fiche" in FicheGUI
        gbc.gridx = 0;
        gbc.gridy = 0;
        cyclePanel.add(cycleLabel, gbc);

        // Initialize resources panel
        resourcesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        updateResourcesDisplay();
        gbc.gridy = 1;
        cyclePanel.add(resourcesPanel, gbc);

        setLayout(new BorderLayout());
        add(cyclePanel, BorderLayout.NORTH);

        // Load background images
        loadImages();
        
        tourCenter = new Point(PANEL_SIZE/2, PANEL_SIZE/2);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < tuileRectangles.size(); i++) {
                    if (tuileRectangles.get(i).contains(e.getPoint())) {
                        selectedTuileIndex = i;
                        controller.handleTuileSelection(i); // Delegate to controller
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    public void setController(Plateau_control controller) {
        this.controller = controller;
    }

    private void loadImages() {
        try {
            BufferedImage tempBackground = ImageIO.read(new File(IMAGES_PATH + "/tuiles/Plateau_v1.png"));
            BufferedImage tempTour = ImageIO.read(new File(IMAGES_PATH + "/tuiles/Plateau_tour.png"));
            
            // Resize images to match panel size
            backgroundImage = resizeImage(tempBackground, PANEL_SIZE, PANEL_SIZE);
            tourImage = resizeImage(tempTour, PANEL_SIZE, PANEL_SIZE);
            
            // Apply antialiasing to images
            backgroundImage = applyAntialiasing(backgroundImage);
            tourImage = applyAntialiasing(tourImage);

            System.out.println("Images loaded successfully from: " + IMAGES_PATH);
        } catch (IOException e) {
            System.err.println("Error loading images from: " + IMAGES_PATH);
            e.printStackTrace();
            errorLabel.setVisible(true);
        }
    }

    private BufferedImage applyAntialiasing(BufferedImage image) {
        BufferedImage antialiasedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = antialiasedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return antialiasedImage;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    public void rotateTour() {
        tourAngle += 40; // Keep the 40-degree rotation
        if (tourAngle >= 360) {
            tourAngle -= 360; // Reset after full rotation
        }
        repaint();
    }

    public void setTourAngle(double angle) {
        this.tourAngle = angle % 360;
        repaint();
    }

    public void flipToNight() {
        int w = tourImage.getWidth();
        int h = tourImage.getHeight();
        BufferedImage flipped = new BufferedImage(w, h, tourImage.getType());
        Graphics2D g2d = flipped.createGraphics();
        
        g2d.drawImage(tourImage, 0, 0, w, h, w, 0, 0, h, null);
        g2d.dispose();
        tourImage = flipped;
        repaint();
    }

    private BufferedImage rotateImage(BufferedImage originalImage, double angle) {
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, originalImage.getType());
        Graphics2D g2d = rotated.createGraphics();
        g2d.rotate(Math.toRadians(angle), w/2, h/2);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();
        return rotated;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());
        tourCenter = new Point(getWidth()/2, getHeight()/2);

        // Draw scaled background
        if (backgroundImage != null) {
            int bgSize = (int)(size * BACKGROUND_SCALE);
            int bgX = tourCenter.x - bgSize/2;
            int bgY = tourCenter.y - bgSize/2;
            g2d.drawImage(backgroundImage, bgX, bgY, bgSize, bgSize, null);
        }

        // Draw scaled and centered tour
        if (tourImage != null) {
            int tourSize = (int)(size * TOUR_SCALE);
            BufferedImage rotatedTourImage = rotateImage(tourImage, tourAngle);
            int tourX = tourCenter.x - tourSize/2;
            int tourY = tourCenter.y - tourSize/2;
            g2d.drawImage(rotatedTourImage, tourX, tourY, tourSize, tourSize, null);
        }

        tuileRectangles.clear();
        drawNonagonAndTuiles(g2d, size);
    }

    private void drawDiceIndicator(Graphics2D g2d, De de, int x, int y, int size) {
        int diceSquareSize = size / 2; // Make dice size half the size of the tile
        int squareX = x + (size - diceSquareSize) / 2; // Center the dice horizontally
        int squareY = y + (size - diceSquareSize) / 2; // Center the dice vertically

        RoundRectangle2D roundedDice = new RoundRectangle2D.Double(squareX, squareY, diceSquareSize, diceSquareSize, 10, 10); // Less rounded corners

        if (de.getCouleur() == CouleurDe.NOIR) {
            g2d.setColor(Color.BLACK);
            g2d.fill(roundedDice);
            g2d.setColor(Color.WHITE); // White text for black background
        } else if (de.getCouleur() == CouleurDe.TRANSPARENT) {
            g2d.setColor(new Color(255, 255, 255, 128)); // Semi-transparent white
            g2d.fill(roundedDice);
            g2d.setColor(Color.BLACK);
        } else {
            g2d.setColor(getCouleurFromEnum(Couleur.fromCouleurDe(de.getCouleur())));
            g2d.fill(roundedDice);
            g2d.setColor(Color.BLACK);
        }

        // Draw dice value
        g2d.setFont(new Font("Arial", Font.BOLD, diceSquareSize / 2));
        String diceValue = String.valueOf(de.getValeur());
        FontMetrics fm = g2d.getFontMetrics();
        int textX = squareX + (diceSquareSize - fm.stringWidth(diceValue)) / 2;
        int textY = squareY + (diceSquareSize + fm.getAscent()) / 2;
        g2d.drawString(diceValue, textX, textY);

        // Draw rounded border
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(roundedDice);
    }

    private void drawNonagonAndTuiles(Graphics2D g2d, int panelSize) {
        // Calculate sizes based on panel size
        int tourSize = (int)(panelSize * TOUR_SCALE);
        int nonagonRadius = (int)(tourSize/2 * NONAGON_SCALE);
        int scaledTuileSize = (int)(nonagonRadius * TUILE_SCALE);

        // Calculate points with debug visualization
        Point2D.Double[] points = new Point2D.Double[9];
        // Draw nonagon outline for debugging
        int[] xPoints = new int[9];
        int[] yPoints = new int[9];

        for (int i = 0; i < 9; i++) {
            // Start from 20 degrees offset (290 instead of 270)
            double angle = Math.toRadians(290 + (i * 40));
            double x = tourCenter.x + nonagonRadius * Math.cos(angle);
            double y = tourCenter.y + nonagonRadius * Math.sin(angle);
            points[i] = new Point2D.Double(x, y);
            xPoints[i] = (int)x;
            yPoints[i] = (int)y;

            // Debug: draw connection points
            g2d.setColor(new Color(255, 0, 0, 128));
            g2d.fillOval((int)x - 3, (int)y - 3, 6, 6);
        }

        // Debug: draw nonagon outline
        g2d.setColor(new Color(255, 255, 255, 64));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(xPoints, yPoints, 9);

        int firstDicePosition = controller.positionPremierDe();

        // Draw tuiles
        for (int i = 0; i < plateau.getListeTuiles().size() && i < 9; i++) {
            int adjustedIndex = (i + 5) % 9; // Adjust index to start from position 5
            Tuile tuile = plateau.getListeTuiles().get(adjustedIndex);
            int x = (int)(points[i].x - scaledTuileSize/2);
            int y = (int)(points[i].y - scaledTuileSize/2);

            RoundRectangle2D roundedRect = new RoundRectangle2D.Double(x, y, scaledTuileSize, scaledTuileSize, 20, 20);
            tuileRectangles.add(roundedRect);

            // Draw tuile with shadow effect
            g2d.setColor(new Color(0, 0, 0, 32));
            g2d.fill(new RoundRectangle2D.Double(x + 2, y + 2, scaledTuileSize, scaledTuileSize, 20, 20));

            g2d.setColor(getCouleurFromEnum(tuile.getCouleur()));
            g2d.fill(roundedRect);

            // Draw dice indicator in the center of the tile
            for (int j = 0; j < plateau.getListesDes().size(); j++) {
                int dicePosition = (firstDicePosition + j) % 9;
                if (dicePosition == adjustedIndex) {
                    De de = plateau.getListesDes().get(j);
                    drawDiceIndicator(g2d, de, x, y, scaledTuileSize);
                }
            }

            // Enhanced border drawing
            if (i == selectedTuileIndex) {
                g2d.setColor(new Color(0, 128, 255, 200));
                g2d.setStroke(new BasicStroke(4));
            } else {
                g2d.setColor(new Color(0, 0, 0, 128));
                g2d.setStroke(new BasicStroke(Math.max(1, panelSize/600)));
            }
            g2d.draw(roundedRect);

            // Improved text rendering
            g2d.setColor(Color.BLACK);
            int fontSize = Math.max(10, scaledTuileSize/4);
            g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
            String couleurRetournee = tuile.getCouleurRetourner().toString();
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (scaledTuileSize - fm.stringWidth(couleurRetournee))/2;
            int textY = y + scaledTuileSize - fm.getDescent();
            g2d.drawString(couleurRetournee, textX, textY);
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return MIN_SIZE;
    }

    private void deSelectionne(int index) {
        if (controller != null) {
            controller.handleTuileSelection(index);
        }
        repaint();
    }

    private Color getCouleurFromEnum(Couleur couleur) {
        switch (couleur) {
            case BLANC: return Color.WHITE;
            case ROUGE: return Color.RED;
            case JAUNE: return Color.YELLOW;
            default: return Color.GRAY;
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void updateGUI() {
        controller.updateGUI();
        cycleLabel.setText(partie.currentCycle + " - Tour " + partie.getJours());
        updateResourcesDisplay();
    }

    public void refreshPlateau() {
        updateGUI();
    }

    public void updateCycleLabel(String cycle, int tour) {
        if (cycleLabel != null){
            cycleLabel.setText(cycle + " - Tour " + tour);
        }
    }

    public void showResourceSelectionPopup() {
        // Create a dialog for resource selection
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Choisissez une ressource", true);
        dialog.setLayout(new BorderLayout());

        // Create a panel to hold the resource buttons
        JPanel resourcePanel = new JPanel(new GridLayout(1, 3, 10, 10));
        resourcePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons for each resource
        JButton influenceButton = createResourceButton("Influence (R)", GMC_IMAGE_PATH);
        JButton deniersButton = createResourceButton("Deniers (J)", INFO_IMAGE_PATH);
        JButton acryliqueButton = createResourceButton("Acrylique (B)", EDIM_IMAGE_PATH);

        // Add action listeners to the buttons
        influenceButton.addActionListener(e -> {
            handleResourceSelection(Ressources.DRAPEAUX);
            dialog.dispose();
        });

        deniersButton.addActionListener(e -> {
            handleResourceSelection(Ressources.ARGENT);
            dialog.dispose();
        });

        acryliqueButton.addActionListener(e -> {
            handleResourceSelection(Ressources.CONNAISSANCE);
            dialog.dispose();
        });

        // Add buttons to the panel
        resourcePanel.add(influenceButton);
        resourcePanel.add(deniersButton);
        resourcePanel.add(acryliqueButton);

        // Add the panel to the dialog
        dialog.add(resourcePanel, BorderLayout.CENTER);

        // Set dialog properties
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JButton createResourceButton(String text, String imagePath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        return button;
    }

    private void handleResourceSelection(Ressources resource) {
        Joueur joueur = partie.getListeJoueurs().get(0);
        joueur.retirerRessource(resource, 1);
        partie.getFenetrePrincipale().reload_fenetre(joueur);
    }

    private void updateResourcesDisplay() {
        resourcesPanel.removeAll();

        Joueur joueur = partie.getListeJoueurs().get(0);
        int nb_RessD = joueur.getInventaireRes().get(Ressources.DRAPEAUX);
        int nb_RessO = joueur.getInventaireRes().get(Ressources.ARGENT);
        int nb_RessC = joueur.getInventaireRes().get(Ressources.CONNAISSANCE);

        resourcesPanel.add(createResourceLabel(GMC_IMAGE_PATH, nb_RessD));
        resourcesPanel.add(createResourceLabel(INFO_IMAGE_PATH, nb_RessO));
        resourcesPanel.add(createResourceLabel(EDIM_IMAGE_PATH, nb_RessC));

        resourcesPanel.revalidate();
        resourcesPanel.repaint();
    }

    private JLabel createResourceLabel(String imagePath, int count) {
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(String.valueOf(count), icon, SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.RIGHT);
        label.setVerticalTextPosition(SwingConstants.CENTER);
        return label;
    }
}