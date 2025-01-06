import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlateauGUI extends JPanel {
    private Plateau plateau;
    private Partie partie;
    private static final int PANEL_SIZE = 600;
    private static final int TUILE_SIZE = 80;
    private ArrayList<Rectangle2D> tuileRectangles;
    private int selectedTuileIndex = -1;
    
    // New fields for background images
    private static final String IMAGES_PATH = "src/ressources/tuiles";
    private BufferedImage backgroundImage;
    private BufferedImage tourImage;
    private double tourAngle = 0;
    private JLabel errorLabel;
    private Point tourCenter;
    private static final double TOUR_SCALE = 0.8; // 80% of panel size for tour
    private static final double BACKGROUND_SCALE = 1.0; // 100% of panel size for background
    private static final double NONAGON_SCALE = 0.9; // 90% of tour radius
    private static final double TUILE_SCALE = 0.35; // 35% of nonagon radius

    public PlateauGUI(Plateau plateau, Partie partie) {
        this.plateau = plateau;
        this.partie = partie;
        this.tuileRectangles = new ArrayList<>();
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        
        // Initialize error label
        errorLabel = new JLabel("Image could not be loaded", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        add(errorLabel);
        
        // Load background images
        loadImages();
        
        tourCenter = new Point(PANEL_SIZE/2, PANEL_SIZE/2);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < tuileRectangles.size(); i++) {
                    if (tuileRectangles.get(i).contains(e.getPoint())) {
                        selectedTuileIndex = i;
                        deSelectionne(i);
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    private void loadImages() {
        try {
            BufferedImage tempBackground = ImageIO.read(new File(IMAGES_PATH + "/Plateau_v1.png"));
            BufferedImage tempTour = ImageIO.read(new File(IMAGES_PATH + "/Plateau_tour.png"));
            
            // Resize images to match panel size
            backgroundImage = resizeImage(tempBackground, PANEL_SIZE, PANEL_SIZE);
            tourImage = resizeImage(tempTour, PANEL_SIZE, PANEL_SIZE);
            
            System.out.println("Images loaded successfully from: " + IMAGES_PATH);
        } catch (IOException e) {
            System.err.println("Error loading images from: " + IMAGES_PATH);
            e.printStackTrace();
            errorLabel.setVisible(true);
        }
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
        tourAngle += 40;
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
            // Start from top (270 degrees) and go clockwise
            double angle = Math.toRadians(270 + (i * 40));
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

        // Draw tuiles
        for (int i = 0; i < plateau.getListeTuiles().size() && i < 9; i++) {
            Tuile tuile = plateau.getListeTuiles().get(i);
            int x = (int)(points[i].x - scaledTuileSize/2);
            int y = (int)(points[i].y - scaledTuileSize/2);
            
            Rectangle2D rect = new Rectangle2D.Double(x, y, scaledTuileSize, scaledTuileSize);
            tuileRectangles.add(rect);
            
            // Draw tuile with shadow effect
            g2d.setColor(new Color(0, 0, 0, 32));
            g2d.fillRect(x + 2, y + 2, scaledTuileSize, scaledTuileSize);
            
            g2d.setColor(getCouleurFromEnum(tuile.getCouleur()));
            g2d.fill(rect);
            
            // Enhanced border drawing
            if (i == selectedTuileIndex) {
                g2d.setColor(new Color(0, 128, 255, 200));
                g2d.setStroke(new BasicStroke(4));
            } else {
                g2d.setColor(new Color(0, 0, 0, 128));
                g2d.setStroke(new BasicStroke(Math.max(1, panelSize/600)));
            }
            g2d.draw(rect);
            
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
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_SIZE, PANEL_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(PANEL_SIZE/2, PANEL_SIZE/2);
    }

    private void deSelectionne(int index) {
        
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
}