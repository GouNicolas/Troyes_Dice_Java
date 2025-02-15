import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class ChangementDeGUI extends JPanel {
    private static final Dimension BUTTON_SIZE = new Dimension(50, 50);
    private static final Dimension DICE_SIZE = new Dimension(60, 60);
    // Calculate minimum width based on 5 buttons plus padding
    private static final int BUTTON_PADDING = 10; // Padding between buttons
    private static final int PANEL_PADDING = 20; // Padding on panel edges
    private static final int MIN_WIDTH = (BUTTON_SIZE.width * 5) + (BUTTON_PADDING * 4) + (PANEL_PADDING * 2);
    private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, 400);

    private JPanel dice;
    private JButton colorButton1;
    private JButton colorButton2;
    private Color squareColor;
    private Color buttonColor1;
    private Color buttonColor2;
    private JLabel numberLabel;
    private JPanel valueButtonPanel;
    private int initialNumber;
    private Color initialColor;

    private Color currentColor;

    private int lockedNumber;
    private Color lockedColor;
    private boolean isDiceLocked = false;
    private JButton lockButton;
    private JLabel scoreLabel;
    private Joueur joueur;
    private FicheGUI ficheGUI;

    public ChangementDeGUI(FicheGUI ficheGUI, Joueur joueur_) {
        // Apply Nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.ficheGUI = ficheGUI;
        this.joueur = joueur_;
        setLayout(new BorderLayout());

        // Main panel with fixed layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        // "Dé choisi" text
        JLabel deChoisiLabel = new JLabel("Dé choisi");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(deChoisiLabel, gbc);

        // Dice button with random number and color
        dice = new JPanel();
        dice.setPreferredSize(new Dimension(50, 50)); // Match the size of color buttons
        dice.setMinimumSize(new Dimension(50, 50)); // Set minimum size
        dice.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Match the border style of color buttons
        numberLabel = new JLabel();
        numberLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dice.add(numberLabel);
        setRandomSquare();

        initialNumber = Integer.parseInt(numberLabel.getText());
        initialColor = dice.getBackground();
        currentColor = dice.getBackground();

        // Add a button to lock the dice
        lockButton = new JButton();
        lockButton.setLayout(new BorderLayout());
        lockButton.add(dice, BorderLayout.CENTER);
        lockButton.setPreferredSize(new Dimension(50, 50)); // Match the size of color buttons
        lockButton.setMinimumSize(new Dimension(50, 50)); // Set minimum size
        // Apply Nimbus look and feel to lockButton
        lockButton.putClientProperty("Nimbus.Overrides", new UIDefaults() {{
            put("Button[Enabled].backgroundPainter", new Painter<JComponent>() {
                @Override
                public void paint(Graphics2D g, JComponent c, int w, int h) {
                    g.setColor(UIManager.getColor("control"));
                    g.fillRect(0, 0, w, h);
                }
            });
        }});
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDiceLocked = true;
                lockButton.setEnabled(false);
                lockedNumber = Integer.parseInt(numberLabel.getText());
                lockedColor = dice.getBackground();
                ficheGUI.updateButtonStates(lockedNumber, lockedColor);
            }
        });

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(lockButton, gbc);

        // "Modify color" text
        JLabel modifyColorLabel = new JLabel("Modify color");
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(modifyColorLabel, gbc);

        // Color buttons
        colorButton1 = new JButton();
        colorButton1.setPreferredSize(new Dimension(50, 50));
        colorButton1.setMinimumSize(new Dimension(50, 50)); // Set minimum size
        colorButton2 = new JButton();
        colorButton2.setPreferredSize(new Dimension(50, 50));
        colorButton2.setMinimumSize(new Dimension(50, 50)); // Set minimum size
        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Center the buttons with a gap of 10 pixels
        buttonPanel.add(colorButton1);
        buttonPanel.add(colorButton2);

        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns to center the panel
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        setRandomButtonColors();

        colorButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDiceLocked && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)>0) {
                    swapColors(colorButton1.getBackground());
                }
            }
        });

        colorButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDiceLocked && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)>0) {
                    swapColors(colorButton2.getBackground());
                }
            }
        });

        // "Modify value" text
        JLabel modifyValueLabel = new JLabel("Modify value");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(modifyValueLabel, gbc);

        // Value buttons
        valueButtonPanel = new JPanel(new GridLayout(1, 6, 10, 0)); // Use GridLayout to avoid cutting buttons
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(valueButtonPanel, gbc);

        updateValueButtons(this.joueur);

        // "Reset dice" button
        JButton resetButton = new JButton("Reset dice");
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(resetButton, gbc);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDiceLocked) {
                    resetDice();
                }
            }
        });

        // Initialize scoreLabel
        scoreLabel = new JLabel("Score : 0");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        mainPanel.add(scoreLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Method to update the score
    public void updateScore(Joueur joueur) {
        int score = joueur.calculerScore(); // Call the method to calculate the score
        scoreLabel.setText("Score : "+score); // Update the score label
    }

    private void setRandomSquare() {
        Random rand = new Random();
        initialNumber = rand.nextInt(6) + 1;
        numberLabel.setText(String.valueOf(initialNumber));

        Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
        initialColor = colors[rand.nextInt(colors.length)];
        squareColor = initialColor;
        dice.setBackground(squareColor);
        dice.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Match the border style of color buttons
    }

    private void setRandomButtonColors() {
        Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
        Color color1 = null;
        Color color2 = null;
    
        if (squareColor.equals(Color.RED)) {
            color1 = Color.YELLOW;
            color2 = Color.WHITE;
        } else if (squareColor.equals(Color.YELLOW)) {
            color1 = Color.RED;
            color2 = Color.WHITE;
        } else if (squareColor.equals(Color.WHITE)) {
            color1 = Color.RED;
            color2 = Color.YELLOW;
        }
    
        buttonColor1 = color1;
        buttonColor2 = color2;
    
        colorButton1.setBackground(buttonColor1);
        colorButton2.setBackground(buttonColor2);
    }

    public void swapColors(Color newColor) {
        System.out.println("Swap colors from " + squareColor + " to " + newColor);
        currentColor = newColor;
        
        Color oldColor = squareColor;
        if (oldColor.equals(newColor)) {
            return;
        }
        else {
            squareColor = newColor;
        }

        dice.setBackground(squareColor);

        if (colorButton1.getBackground().equals(newColor)) {
            System.out.println("New color is button 1" + newColor);
            colorButton1.setBackground(oldColor);
        } else {
            colorButton2.setBackground(oldColor);
        }
    }

    public void setDe(De de) {
        if (de == null) {
            System.err.println("Error: De object is null");
            return;
        }
        initialNumber = de.getValeur();
        initialColor = Couleur.convertCouleurDeToColor(de.getCouleur());


        swapColors(Couleur.convertCouleurDeToColor(de.getCouleur()));
        numberLabel.setText(String.valueOf(de.getValeur()));
        updateValueButtons(this.joueur);
    }

    private void updateValueButtons(Joueur joueur) {
        valueButtonPanel.removeAll();
        int currentValue = Integer.parseInt(numberLabel.getText());
        for (int i = 1; i <= 6; i++) {
            System.out.println("i: " + initialNumber + " currentValue: " + currentValue);
            
            int Diff = Math.abs(i - initialNumber);

            if (i != currentValue && joueur.getInventaireRes().get(Ressources.DRAPEAUX) >= Diff) {
                JButton valueButton = new JButton(String.valueOf(i));
                valueButton.setPreferredSize(new Dimension(50, 50));
                valueButton.addActionListener(new ActionListener() {
                    @Override ///LAAAA
                    public void actionPerformed(ActionEvent e) {
                        if (!isDiceLocked) {
                            swapValues(Integer.parseInt(valueButton.getText()));
                            updateValueButtons(joueur); // Mettre à jour les boutons après le changement de valeur
                        }
                    }
                });
                valueButtonPanel.add(valueButton);
            }
        }
        valueButtonPanel.revalidate();
        valueButtonPanel.repaint();
    }

    private void swapValues(int newValue) {
        numberLabel.setText(String.valueOf(newValue));
        updateValueButtons(this.joueur); // Met à jour les boutons après avoir changé la valeur du dé
    }

    public boolean isLocked() {
        return isDiceLocked;
    }

    private void resetDice() {
        numberLabel.setText(String.valueOf(initialNumber));
        dice.setBackground(initialColor);
        squareColor = initialColor;
        setRandomButtonColors();
        updateValueButtons(this.joueur);
    }

    public void resetAndUnlockDice() {
        resetDice();
        isDiceLocked = false;
        lockButton.setEnabled(true);
    }

    public int getLockedDiceValue() {
        return lockedNumber;
    }

    public Color getLockedDiceColor() {
        return lockedColor;
    }

    public void rerollDice() {
        setRandomSquare();
        setRandomButtonColors();
        updateValueButtons(this.joueur);
        isDiceLocked = false;
        lockButton.setEnabled(true);
    }
}