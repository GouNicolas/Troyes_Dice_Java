import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class FicheGUI extends JFrame {
    private FicheController controller;
    private List<JPanel> resourcesPanels;
    private JPanel fichePanel;

    public FicheGUI(FicheController controller) {
        System.out.println("Creating FicheGUI");
        this.controller = controller;
        resourcesPanels = new ArrayList<>();

        setTitle("Fiche Information");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel for the frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel for the "fiche"
        fichePanel = new JPanel();
        fichePanel.setLayout(new BoxLayout(fichePanel, BoxLayout.Y_AXIS));
        fichePanel.setBorder(BorderFactory.createTitledBorder("Fiche"));

        // Title
        JLabel titleLabel = new JLabel("Fiche", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fichePanel.add(titleLabel);

        // Add space after title
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Red rectangle
        JPanel redPanel = createColoredPanel(Color.RED, 0);
        fichePanel.add(redPanel);

        // Add space between rectangles
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Yellow rectangle
        JPanel yellowPanel = createColoredPanel(Color.YELLOW, 1);
        fichePanel.add(yellowPanel);

        // Add space between rectangles
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // White rectangle
        JPanel whitePanel = createColoredPanel(Color.WHITE, 2);
        fichePanel.add(whitePanel);

        // Add the fiche panel to the main panel
        mainPanel.add(fichePanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add other components to fill the remaining space
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 600));
        leftPanel.setBackground(Color.GRAY);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100, 600));
        rightPanel.setBackground(Color.GRAY);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 50));
        topPanel.setBackground(Color.GRAY);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 50));
        bottomPanel.setBackground(Color.GRAY);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createColoredPanel(Color color, int rowIndex) {
        JPanel coloredPanel = new JPanel();
        coloredPanel.setBackground(color);
        coloredPanel.setOpaque(true);
        coloredPanel.setPreferredSize(new Dimension(700, 350));
        coloredPanel.setLayout(new BoxLayout(coloredPanel, BoxLayout.Y_AXIS));

        // Add 3 lines with height 100, each containing 6 squares and small cases between some squares
        for (int i = 0; i < 3; i++) {
            JPanel linePanel = new JPanel();
            linePanel.setPreferredSize(new Dimension(700, 100));
            linePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            linePanel.setOpaque(false);
            linePanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridy = 0;
            gbc.weighty = 1.0;

            int smallCaseCount = 0;
            for (int j = 0; j < 6; j++) {
                if (j < 5 && controller.hasSmallCase(rowIndex * 3 + i, j, j + 1)) {
                    smallCaseCount++;
                }
            }

            System.out.println("Small case count: " + smallCaseCount);

            int totalWidth = 700;
            int smallCaseWidth = 10;
            int booleanSquareWidth = (totalWidth - (smallCaseCount * smallCaseWidth)) / (6 + smallCaseCount);

            for (int j = 0; j < 6; j++) {
                // Add JLabel with boolean value from the controller
                boolean value = controller.getValue(rowIndex * 3 + i, j);
                JLabel booleanLabel = new JLabel(String.valueOf(value), SwingConstants.CENTER);
                booleanLabel.setBackground(color);
                booleanLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                booleanLabel.setOpaque(true);
                booleanLabel.setPreferredSize(new Dimension(booleanSquareWidth, 100)); // Adjust the size of the boolean square

                gbc.gridx = j * 2;
                gbc.weightx = 1.0;
                linePanel.add(booleanLabel, gbc);

                // Check if a small case should be added after this square
                if (j < 5 && controller.hasSmallCase(rowIndex * 3 + i, j, j + 1)) {
                    int smallCaseValue = controller.getSmallCaseValue(rowIndex * 3 + i, j, j + 1);
                    JLabel smallCaseLabel = new JLabel(String.valueOf(smallCaseValue), SwingConstants.CENTER);
                    smallCaseLabel.setBackground(Color.LIGHT_GRAY);
                    smallCaseLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    smallCaseLabel.setOpaque(true);
                    smallCaseLabel.setPreferredSize(new Dimension(smallCaseWidth, 100)); // Adjust the size of the small case

                    gbc.gridx = j * 2 + 1;
                    gbc.weightx = 0.1;
                    linePanel.add(smallCaseLabel, gbc);
                }
            }

            coloredPanel.add(linePanel);
        }

        // Add 1 line with height 50 for resources
        JPanel resourcesPanel = new JPanel();
        resourcesPanel.setPreferredSize(new Dimension(700, 50));
        resourcesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resourcesPanel.setOpaque(false);
        resourcesPanel.setLayout(new GridLayout(1, 21));
        updateResourcesPanel(resourcesPanel, rowIndex);
        coloredPanel.add(resourcesPanel);
        resourcesPanels.add(resourcesPanel);

        return coloredPanel;
    }

    private void updateResourcesPanel(JPanel resourcesPanel, int blockIndex) {
        resourcesPanel.removeAll();
        char[] resources = controller.getResources().get(blockIndex);
        for (char resource : resources) {
            JLabel resourceLabel = new JLabel(String.valueOf(resource), SwingConstants.CENTER);
            resourcesPanel.add(resourceLabel);
        }
        resourcesPanel.revalidate();
        resourcesPanel.repaint();
    }

    public void updateContent(Fiche fiche) {
        fichePanel.removeAll(); // Clear the existing content

        // Re-add the title
        JLabel titleLabel = new JLabel("Fiche", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        controller.initialisermatrix(fiche);

        fichePanel.add(titleLabel);

        // Add space after title
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Re-create and add the colored panels
        JPanel redPanel = createColoredPanel(Color.RED, 0);
        fichePanel.add(redPanel);

        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel yellowPanel = createColoredPanel(Color.YELLOW, 1);
        fichePanel.add(yellowPanel);

        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel whitePanel = createColoredPanel(Color.WHITE, 2);
        fichePanel.add(whitePanel);

        fichePanel.revalidate();
        fichePanel.repaint();
    }
}