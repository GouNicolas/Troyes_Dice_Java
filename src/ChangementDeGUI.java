import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class ChangementDeGUI extends JPanel {
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
    private boolean isDiceLocked = false;

    public ChangementDeGUI() {
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

        // Square with random number and color
        dice = new JPanel();
        dice.setPreferredSize(new Dimension(40, 40));
        dice.setMaximumSize(new Dimension(40, 40));
        dice.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        numberLabel = new JLabel();
        numberLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dice.add(numberLabel);
        setRandomSquare();

        // Add a button to lock the dice
        JButton lockButton = new JButton();
        lockButton.setLayout(new BorderLayout());
        lockButton.add(dice, BorderLayout.CENTER);
        lockButton.setPreferredSize(new Dimension(40, 40));
        lockButton.setMaximumSize(new Dimension(40, 40));
        lockButton.setBorder(BorderFactory.createEmptyBorder());
        lockButton.setContentAreaFilled(false);
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDiceLocked = true;
                lockButton.setEnabled(false);
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
        colorButton2 = new JButton();
        colorButton2.setPreferredSize(new Dimension(50, 50));
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
                if (!isDiceLocked) {
                    swapColors(colorButton1.getBackground());
                }
            }
        });

        colorButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDiceLocked) {
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
        valueButtonPanel = new JPanel(new FlowLayout());
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(valueButtonPanel, gbc);

        updateValueButtons();

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

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setRandomSquare() {
        Random rand = new Random();
        initialNumber = rand.nextInt(6) + 1;
        numberLabel.setText(String.valueOf(initialNumber));

        Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
        initialColor = colors[rand.nextInt(colors.length)];
        squareColor = initialColor;
        dice.setBackground(squareColor);
    }

    private void setRandomButtonColors() {
        Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
        Random rand = new Random();

        do {
            buttonColor1 = colors[rand.nextInt(colors.length)];
        } while (buttonColor1.equals(squareColor));

        do {
            buttonColor2 = colors[rand.nextInt(colors.length)];
        } while (buttonColor2.equals(squareColor) || buttonColor2.equals(buttonColor1));

        colorButton1.setBackground(buttonColor1);
        colorButton2.setBackground(buttonColor2);
    }

    private void swapColors(Color newColor) {
        Color oldColor = squareColor;
        squareColor = newColor;
        dice.setBackground(squareColor);

        if (colorButton1.getBackground().equals(newColor)) {
            colorButton1.setBackground(oldColor);
        } else {
            colorButton2.setBackground(oldColor);
        }
    }

    private void updateValueButtons() {
        valueButtonPanel.removeAll();
        int currentValue = Integer.parseInt(numberLabel.getText());
        for (int i = 1; i <= 6; i++) {
            JButton valueButton = new JButton(String.valueOf(i));
            valueButton.setPreferredSize(new Dimension(50, 50));
            valueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isDiceLocked) {
                        swapValues(Integer.parseInt(valueButton.getText()));
                    }
                }
            });
            valueButtonPanel.add(valueButton);
        }
        valueButtonPanel.revalidate();
        valueButtonPanel.repaint();
    }

    private void swapValues(int newValue) {
        numberLabel.setText(String.valueOf(newValue));
    }

    private void resetDice() {
        setRandomSquare();
        setRandomButtonColors();
        updateValueButtons();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Changement De GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new ChangementDeGUI());
        frame.setVisible(true);
    }
}