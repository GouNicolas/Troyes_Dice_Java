import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class FicheGUI extends JFrame {
    private ChangementDeGUI changementDeGUIPanel;
    private FicheController controller;
    private List<JPanel> resourcesPanels;
    private JPanel fichePanel;
    private Map<String, JLabel> smallCaseLabels;
    private Map<String, JButton> booleanButtons;
    
    public FicheGUI(FicheController controller) {
        this.controller = controller;
        resourcesPanels = new ArrayList<>();
        smallCaseLabels = new HashMap<>();
        booleanButtons = new HashMap<>();
        
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
        fichePanel.setPreferredSize(new Dimension(500, 800));

        // Title
        JLabel titleLabel = new JLabel("Fiche", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fichePanel.add(titleLabel);

        // Add space after title
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create a panel to hold the colored panels
        JPanel coloredPanelsContainer = new JPanel();
        coloredPanelsContainer.setLayout(new BoxLayout(coloredPanelsContainer, BoxLayout.Y_AXIS));

        // Red rectangle
        JPanel redPanel = createColoredPanel(Color.RED, 0);
        coloredPanelsContainer.add(redPanel);

        coloredPanelsContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel yellowPanel = createColoredPanel(Color.YELLOW, 1);
        coloredPanelsContainer.add(yellowPanel);

        coloredPanelsContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel whitePanel = createColoredPanel(Color.WHITE, 2);
        coloredPanelsContainer.add(whitePanel);

        mainPanel.add(coloredPanelsContainer, BorderLayout.CENTER);

        // Create a new panel for the column on the right
        JPanel rightColumnPanel = new JPanel();
        rightColumnPanel.setLayout(new BoxLayout(rightColumnPanel, BoxLayout.Y_AXIS));
        rightColumnPanel.setPreferredSize(new Dimension(100, 350)); // Adjust the width as needed

        // Add 10 lines, each containing 1 small case
        for (int i = 0; i < 10; i++) {
            if (i == 4 || i == 7) {
                rightColumnPanel.add(Box.createRigidArea(new Dimension(0,40))); // Add space every 4 cases
            }
            JPanel linePanel = new JPanel();
            linePanel.setPreferredSize(new Dimension(10, 10)); // Adjust the height as needed
            linePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            linePanel.setOpaque(false);
            linePanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            JPanel smallCase = new JPanel();
            smallCase.setPreferredSize(new Dimension(80, 20)); // Adjust the size as needed
            smallCase.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            linePanel.add(smallCase, gbc);

            rightColumnPanel.add(linePanel);
        }

        mainPanel.add(rightColumnPanel, BorderLayout.EAST);

        fichePanel.add(mainPanel, BorderLayout.CENTER);

        // Add the new area with the left rectangle and the 3 lines
        JPanel newAreaPanel = createNewAreaPanel();
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fichePanel.add(newAreaPanel);

        fichePanel.revalidate();
        fichePanel.repaint();

        // Add the fiche panel to the main panel
        add(fichePanel, BorderLayout.CENTER);
    }

    private JPanel createColoredPanel(Color color, int rowIndex) {
        final ImageIcon trueIcon = new ImageIcon("C:/Users/33787/OneDrive - Université De Technologie De Belfort-Montbeliard/Bureau/UTBM BR01-02/AP4B/Portage UTBM projet AP4B/Autre/Forteresse.png");
        final ImageIcon falseIcon = new ImageIcon("C:/Users/33787/OneDrive - Université De Technologie De Belfort-Montbeliard/Bureau/UTBM BR01-02/AP4B/Portage UTBM projet AP4B/Autre/ForteresseNonPosee.png");
        
        // Resize images
        Image trueImage = trueIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image falseImage = falseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        final ImageIcon resizedtrueIcon = new ImageIcon(trueImage);
        final ImageIcon resizedfalseIcon = new ImageIcon(falseImage);

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

            int totalWidth = 700;
            int smallCaseWidth = 10;
            int booleanSquareWidth = (totalWidth - (smallCaseCount * smallCaseWidth)) / (6 + smallCaseCount);

            for (int j = 0; j < 6; j++) {
                // Add JButton with boolean value from the controller
                boolean value = controller.getValue(rowIndex * 3 + i, j);
                JButton booleanButton = new JButton(value ? resizedtrueIcon : resizedfalseIcon);
                booleanButton.setBackground(color);
                booleanButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                booleanButton.setOpaque(true);
                booleanButton.setPreferredSize(new Dimension(booleanSquareWidth, 100)); // Adjust the size of the boolean square
                booleanButton.setEnabled(false); // Initially disable all buttons
                
                final int row = rowIndex * 3 + i;
                final int col = j;
                booleanButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.setValue(row, col, true);
                        booleanButton.setIcon(resizedtrueIcon);
                        booleanButton.setText("");
                        updateSmallCases(row, col);
                        revalidate();
                        repaint();
      
                         // Disable buttons with false value
                        for (Map.Entry<String, JButton> entry : booleanButtons.entrySet()) {
                            JButton button = entry.getValue();
                            if (button != booleanButton) {
                                button.setEnabled(false);
                            }
                        }
                }
            });
            

                gbc.gridx = j * 2;
                gbc.weightx = 1.0;
                linePanel.add(booleanButton, gbc);

                // Store reference to the boolean button
                booleanButtons.put(row + "-" + col, booleanButton);

                // Check if a small case should be added after this square
                if (j < 5 && controller.hasSmallCase(row, j, j + 1)) {
                    int smallCaseValue = controller.getSmallCaseValue(row, j, j + 1);
                    JLabel smallCaseLabel = new JLabel(String.valueOf(smallCaseValue), SwingConstants.CENTER);
                    smallCaseLabel.setBackground(Color.LIGHT_GRAY);
                    smallCaseLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    smallCaseLabel.setOpaque(true);
                    smallCaseLabel.setPreferredSize(new Dimension(smallCaseWidth, 100)); // Adjust the size of the small case

                    gbc.gridx = j * 2 + 1;
                    gbc.weightx = 0.1;
                    linePanel.add(smallCaseLabel, gbc);

                    // Store reference to the small case label
                    smallCaseLabels.put(row + "-" + j + "-" + (j + 1), smallCaseLabel);
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

        //Add the + button
        JButton plusButton = new JButton("+");
        plusButton.setBackground(color);
        plusButton.setEnabled(false);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton button : booleanButtons.values()) {
                    button.setEnabled(false);
                }
                for (JPanel panel : resourcesPanels) {
                    for (Component component : panel.getComponents()) {
                        if (component instanceof JButton) {
                            ((JButton) component).setEnabled(false);
                        }
                    }
                }
            }
        });
        resourcesPanel.add(plusButton);

        return coloredPanel;
    }

    private void updateSmallCases(int row, int col) {
        for (int j = 0; j < 5; j++) {
            if (controller.getValue(row, j) && controller.getValue(row, j + 1)) {
                int smallCaseValue = controller.getSmallCaseValue(row, j, j + 1);
                // Update the small case value in the UI
                JLabel smallCaseLabel = smallCaseLabels.get(row + "-" + j + "-" + (j + 1));
                if (smallCaseLabel != null) {
                    smallCaseLabel.setText(String.valueOf(smallCaseValue));
                }
            }
        }
    }

    private JPanel createNewRectanglePanel() {
        JPanel newRectanglePanel = new JPanel();
        newRectanglePanel.setLayout(new BoxLayout(newRectanglePanel, BoxLayout.Y_AXIS));
        newRectanglePanel.setPreferredSize(new Dimension(700, 300));

        // Create the 3 lines with different colors
        Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
        for (Color color : colors) {
            JPanel linePanel = new JPanel();
            linePanel.setPreferredSize(new Dimension(700, 100));
            linePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            linePanel.setOpaque(true);
            linePanel.setBackground(color);
            linePanel.setLayout(new GridLayout(1, 20));

            // Add 20 values initialized to 0
            for (int i = 0; i < 20; i++) {
                JLabel valueLabel = new JLabel("0", SwingConstants.CENTER);
                valueLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                linePanel.add(valueLabel);
            }

            newRectanglePanel.add(linePanel);
        }

        return newRectanglePanel;
    }

    private JPanel createNewLeftRectanglePanel() {
        JPanel newLeftRectanglePanel = new JPanel();
        newLeftRectanglePanel.setLayout(new BoxLayout(newLeftRectanglePanel, BoxLayout.Y_AXIS));
        newLeftRectanglePanel.setPreferredSize(new Dimension(100, 300));

        // First line with one case with nothing inside
        JPanel line1 = new JPanel();
        line1.setPreferredSize(new Dimension(100, 75));
        line1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        line1.setOpaque(true);
        line1.setBackground(Color.LIGHT_GRAY);
        line1.setLayout(new GridLayout(1, 1));
        newLeftRectanglePanel.add(line1);

        // Second line with 3 cases with values 1, 3, 5
        JPanel line2 = new JPanel();
        line2.setPreferredSize(new Dimension(100, 75));
        line2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        line2.setOpaque(true);
        line2.setBackground(Color.LIGHT_GRAY);
        line2.setLayout(new GridLayout(1, 3));
        line2.add(new JLabel("1", SwingConstants.CENTER));
        line2.add(new JLabel("3", SwingConstants.CENTER));
        line2.add(new JLabel("5", SwingConstants.CENTER));
        newLeftRectanglePanel.add(line2);

        // Third line with 3 cases with values 2, 4, 6
        JPanel line3 = new JPanel();
        line3.setPreferredSize(new Dimension(100, 75));
        line3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        line3.setOpaque(true);
        line3.setBackground(Color.LIGHT_GRAY);
        line3.setLayout(new GridLayout(1, 3));
        line3.add(new JLabel("2", SwingConstants.CENTER));
        line3.add(new JLabel("4", SwingConstants.CENTER));
        line3.add(new JLabel("6", SwingConstants.CENTER));
        newLeftRectanglePanel.add(line3);

        // Fourth line with 3 cases with values x1, x2, x3
        JPanel line4 = new JPanel();
        line4.setPreferredSize(new Dimension(100, 75));
        line4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        line4.setOpaque(true);
        line4.setBackground(Color.LIGHT_GRAY);
        line4.setLayout(new GridLayout(1, 3));
        line4.add(new JLabel("x1", SwingConstants.CENTER));
        line4.add(new JLabel("x2", SwingConstants.CENTER));
        line4.add(new JLabel("x3", SwingConstants.CENTER));
        newLeftRectanglePanel.add(line4);

        return newLeftRectanglePanel;
    }

    private JPanel createNewAreaPanel() {
        JPanel newAreaPanel = new JPanel();
        newAreaPanel.setLayout(new BorderLayout());

        // Add the new left rectangle to the left of the new area
        JPanel newLeftRectanglePanel = createNewLeftRectanglePanel();
        newAreaPanel.add(newLeftRectanglePanel, BorderLayout.WEST);

        // Add the new rectangle with 3 lines to the center of the new area
        JPanel newRectanglePanel = createNewRectanglePanel();
        newAreaPanel.add(newRectanglePanel, BorderLayout.CENTER);

        return newAreaPanel;
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

    public void updateContent(Fiche fiche, Joueur joueur) {
        fichePanel.removeAll(); // Clear the existing content

        // Re-add the title
        JLabel titleLabel = new JLabel("Fiche", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        controller.initialisermatrix(fiche);
        controller.initialiserResources(joueur);

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

        // Add the new area with the left rectangle and the 3 lines
        JPanel newAreaPanel = createNewAreaPanel();
        fichePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fichePanel.add(newAreaPanel);

        fichePanel.revalidate();
        fichePanel.repaint();
    }

    public JPanel getMainPanel() {
        return fichePanel;
    }

    public void updateButtonStates(int diceValue, Color diceColor) {
        // Disable all buttons initially
        for (JButton button : booleanButtons.values()) {
            button.setEnabled(false);
        }

        // Enable buttons based on dice value and color
        int columnIndex = diceValue - 1;
        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            JButton button = booleanButtons.get(rowIndex + "-" + columnIndex);
            if (button != null && button.getBackground().equals(diceColor)) {
                button.setEnabled(true);
            }
        }

        // Enable the + button
        for (JPanel resourcesPanel : resourcesPanels) {
            for (Component component : resourcesPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    if (button.getText().equals("+")) {
                        button.setEnabled(button.getBackground().equals(diceColor));
                    }
                }
            }
        }
    }
}