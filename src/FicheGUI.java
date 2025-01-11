import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class FicheGUI extends JFrame {
    private ChangementDeGUI changementDeGUIPanel;
    private FicheController controller;
    private List<JPanel> resourcesPanels;
    private JPanel fichePanel;
    private Map<String, JLabel> smallCaseLabels;
    private Map<String, JButton> booleanButtons;

    private Partie partie;
    
    public FicheGUI(FicheController controller) {
        this.controller = controller;
        resourcesPanels = new ArrayList<>();
        smallCaseLabels = new HashMap<>();
        booleanButtons = new HashMap<>();
        changementDeGUIPanel = new ChangementDeGUI(this);
        
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

    public void setPartieJeu(Partie partie) {
        this.partie = partie;
    }

    public ChangementDeGUI getChangementDeGUIPanel() {
        return changementDeGUIPanel;
    }

    private JPanel createColoredPanel(Color color, int rowIndex) {
        
        final ImageIcon FortIcon = new ImageIcon("./ressources/Portage/Autre/Forteresse.png");
        final ImageIcon FortNPIcon = new ImageIcon("./ressources/Portage/Autre/ForteresseNonPosee.png");
        
        final ImageIcon red11Icon = new ImageIcon("./ressources/Portage/GMC/Gmc1.png");
        final ImageIcon red11NPIcon = new ImageIcon("./ressources/Portage/GMC/GmcPeuple.png");
        final ImageIcon red12Icon = new ImageIcon("./ressources/Portage/GMC/Gmc1.png");
        final ImageIcon red12NPIcon = new ImageIcon("./ressources/Portage/Info/InfoPeuple.png");
        final ImageIcon red13Icon = new ImageIcon("./ressources/Portage/GMC/Gmc1.png");
        final ImageIcon red13NPIcon = new ImageIcon("./ressources/Portage/Edim/EdimPeuple.png");
        final ImageIcon red2Icon = new ImageIcon("./ressources/Portage/GMC/Gmc2.png");
        final ImageIcon red2NPIcon = new ImageIcon("./ressources/Portage/Plateau/RewardGmc.png");

        final ImageIcon yel1Icon = new ImageIcon("./ressources/Portage/Info/Info1.png");
        final ImageIcon yel11NPIcon = new ImageIcon("./ressources/Portage/Plateau/Case1-QuartierInfo.png");
        final ImageIcon yel12NPIcon = new ImageIcon("./ressources/Portage/Plateau/Case2-QuartierInfo.png");
        final ImageIcon yel13NPIcon = new ImageIcon("./ressources/Portage/Plateau/Case3-QuartierInfo.png");
        final ImageIcon yel14NPIcon = new ImageIcon("./ressources/Portage/Plateau/Case4-QuartierInfo.png");
        final ImageIcon yel15NPIcon = new ImageIcon("./ressources/Portage/Plateau/Case5-QuartierInfo.png");
        final ImageIcon yel16NPIcon = new ImageIcon("./ressources/Portage/Plateau/Case6-QuartierInfo.png");
        final ImageIcon yel2Icon = new ImageIcon("./ressources/Portage/Info/Info2.png");
        final ImageIcon yel2NPIcon = new ImageIcon("./ressources/Portage/Plateau/RewardInfo.png");

        final ImageIcon white1Icon = new ImageIcon("src/ressources/Portage/Edim/Edim1.png");
        final ImageIcon white1NPIcon = new ImageIcon("src/ressources/Portage/Edim/Edim1NonPose.png");
        final ImageIcon white2Icon = new ImageIcon("src/ressources/Portage/Edim/Edim2.png");
        final ImageIcon white2NPIcon = new ImageIcon("src/ressources/Portage/Plateau/RewardEdim.png");

        // Resize images
        Image FortImage = FortIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image FortNPImage = FortNPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        Image red11Image = red11Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red11NPImage = red11NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red12Image = red12Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red12NPImage = red12NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red13Image = red13Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red13NPImage = red13NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red2Image = red2Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image red2NPImage = red2NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        Image yel1Image = yel1Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel11NPImage = yel11NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel12NPImage = yel12NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel13NPImage = yel13NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel14NPImage = yel14NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel15NPImage = yel15NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel16NPImage = yel16NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel2Image = yel2Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image yel2NPImage = yel2NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        
        Image white1Image = white1Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image white1NPImage = white1NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image white2Image = white2Icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image white2NPImage = white2NPIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        // Create resized icons
        final ImageIcon resizedFortIcon = new ImageIcon(FortImage);
        final ImageIcon resizedFortNPIcon = new ImageIcon(FortNPImage);

        final ImageIcon resizedred11Icon = new ImageIcon(red11Image);
        final ImageIcon resizedred11NPIcon = new ImageIcon(red11NPImage);
        final ImageIcon resizedred12Icon = new ImageIcon(red12Image);
        final ImageIcon resizedred12NPIcon = new ImageIcon(red12NPImage);
        final ImageIcon resizedred13Icon = new ImageIcon(red13Image);
        final ImageIcon resizedred13NPIcon = new ImageIcon(red13NPImage);
        final ImageIcon resizedred2Icon = new ImageIcon(red2Image);
        final ImageIcon resizedred2NPIcon = new ImageIcon(red2NPImage);

        final ImageIcon resizedyel1Icon = new ImageIcon(yel1Image);
        final ImageIcon resizedyel11NPIcon = new ImageIcon(yel11NPImage);
        final ImageIcon resizedyel12NPIcon = new ImageIcon(yel12NPImage);
        final ImageIcon resizedyel13NPIcon = new ImageIcon(yel13NPImage);
        final ImageIcon resizedyel14NPIcon = new ImageIcon(yel14NPImage);
        final ImageIcon resizedyel15NPIcon = new ImageIcon(yel15NPImage);
        final ImageIcon resizedyel16NPIcon = new ImageIcon(yel16NPImage);
        final ImageIcon resizedyel2Icon = new ImageIcon(yel2Image);
        final ImageIcon resizedyel2NPIcon = new ImageIcon(yel2NPImage);

        final ImageIcon resizedWhite1Icon = new ImageIcon(white1Image);
        final ImageIcon resizedWhite1NPIcon = new ImageIcon(white1NPImage);
        final ImageIcon resizedWhite2Icon = new ImageIcon(white2Image);
        final ImageIcon resizedWhite2NPIcon = new ImageIcon(white2NPImage);

       // Create 2D arrays of images for each color (True and False)
        ImageIcon[][] redTrueIcons = {
            {resizedred11Icon, resizedred11Icon, resizedred12Icon, resizedred12Icon, resizedred13Icon, resizedred13Icon},
            {resizedred2Icon, resizedred2Icon, resizedred2Icon, resizedred2Icon, resizedred2Icon, resizedred2Icon}
        };
        ImageIcon[][] redFalseIcons = {
            {resizedred11NPIcon, resizedred11NPIcon, resizedred12NPIcon, resizedred12NPIcon, resizedred13NPIcon, resizedred13NPIcon},
            {resizedred2NPIcon, resizedred2NPIcon, resizedred2NPIcon, resizedred2NPIcon, resizedred2NPIcon, resizedred2NPIcon}
        };

        ImageIcon[][] yellowTrueIcons = {
            {resizedyel1Icon, resizedyel1Icon, resizedyel1Icon, resizedyel1Icon, resizedyel1Icon, resizedyel1Icon},
            {resizedyel2Icon, resizedyel2Icon, resizedyel2Icon, resizedyel2Icon, resizedyel2Icon, resizedyel2Icon}
        };
        ImageIcon[][] yellowFalseIcons = {
            {resizedyel11NPIcon, resizedyel12NPIcon, resizedyel13NPIcon, resizedyel14NPIcon, resizedyel15NPIcon, resizedyel16NPIcon},
            {resizedyel2NPIcon, resizedyel2NPIcon, resizedyel2NPIcon, resizedyel2NPIcon, resizedyel2NPIcon, resizedyel2NPIcon},
        };

        ImageIcon[][] whiteTrueIcons = {
            {resizedWhite1Icon, resizedWhite1Icon, resizedWhite1Icon, resizedWhite1Icon, resizedWhite1Icon, resizedWhite1Icon},
            {resizedWhite2Icon, resizedWhite2Icon, resizedWhite2Icon, resizedWhite2Icon, resizedWhite2Icon, resizedWhite2Icon}
        };
        ImageIcon[][] whiteFalseIcons = {
            {resizedWhite1NPIcon, resizedWhite1NPIcon, resizedWhite1NPIcon, resizedWhite1NPIcon, resizedWhite1NPIcon, resizedWhite1NPIcon},
            {resizedWhite2NPIcon, resizedWhite2NPIcon, resizedWhite2NPIcon, resizedWhite2NPIcon, resizedWhite2NPIcon, resizedWhite2NPIcon}
        };

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
                ImageIcon trueIcon;
                ImageIcon falseIcon;

                if (i == 0) {
                    // Add JLabel for text display
                    boolean value = controller.getValue(rowIndex * 3 + i, j);
                    JLabel imageLabel = new JLabel(value ? resizedFortIcon : resizedFortNPIcon);
                    imageLabel.setOpaque(true);
                    imageLabel.setBackground(color);
    
                    gbc.gridx = j * 2;
                    gbc.weightx = 1.0;
                    linePanel.add(imageLabel, gbc);
                }else {

                    if (color.equals(Color.RED)) {
                        trueIcon = redTrueIcons[i - 1][j % redTrueIcons[i - 1].length];
                        falseIcon = redFalseIcons[i - 1][j % redFalseIcons[i - 1].length];
                    } else if (color.equals(Color.YELLOW)) {
                        trueIcon = yellowTrueIcons[i - 1][j % yellowTrueIcons[i - 1].length];
                        falseIcon = yellowFalseIcons[i - 1][j % yellowFalseIcons[i - 1].length];
                    } else {
                        trueIcon = whiteTrueIcons[i - 1][j % whiteTrueIcons[i - 1].length];
                        falseIcon = whiteFalseIcons[i - 1][j % whiteFalseIcons[i - 1].length];
                    }

                    // Add JButton with boolean value from the controller
                    boolean value = controller.getValue(rowIndex * 3 + i, j);
                    JButton booleanButton = new JButton(value ? trueIcon : falseIcon);
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
                            handleButtonAction(row, col); // Disable the button after it has been pressed
                            booleanButton.setEnabled(true);
                        }
                    });
    
                    gbc.gridx = j * 2;
                    gbc.weightx = 1.0;
                    linePanel.add(booleanButton, gbc);
    
                    // Store reference to the boolean button
                    booleanButtons.put((rowIndex * 3 + i) + "-" + col, booleanButton);
                }

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

                    // Store reference to the small case label
                    smallCaseLabels.put(rowIndex * 3 + i + "-" + j + "-" + (j + 1), smallCaseLabel);
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
                controller.ajouterRessource(color);
                updateResourcesPanel(resourcesPanel, rowIndex);
                resourcesPanel.add(plusButton); // Re-add the + button after updating the panel
                resourcesPanel.revalidate();
                resourcesPanel.repaint();
            }
        });
        resourcesPanel.add(plusButton);

        return coloredPanel;
    }

    private void handleButtonAction(int row, int col) {
        // Call specific functions based on the row and col values
        if (row == 1) {
            switch (col) { //ROUGE PRESTIGE
                case 0: functionForButtonRP1(); System.out.println("row " + row + "col "+col); break;
                case 1: functionForButtonRP2(); System.out.println("row " + row + "col "+col); break;
                case 2: functionForButtonRP3(); System.out.println("row " + row + "col "+col); break;
                case 3: functionForButtonRP4(); System.out.println("row " + row + "col "+col); break;
                case 4: functionForButtonRP5(); System.out.println("row " + row + "col "+col); break;
                case 5: functionForButtonRP6(); System.out.println("row " + row + "col "+col); break;
            }
        } else if (row == 2) {
            switch (col) { // ROUGE CLASSQUE
                case 0: functionForButtonRC1(); System.out.println("row " + row + "col "+col); break;
                case 1: functionForButtonRC2(); System.out.println("row " + row + "col "+col); break;
                case 2: functionForButtonRC3(); System.out.println("row " + row + "col "+col); break;
                case 3: functionForButtonRC4(); System.out.println("row " + row + "col "+col); break;
                case 4: functionForButtonRC5(); System.out.println("row " + row + "col "+col); break;
                case 5: functionForButtonRC6(); System.out.println("row " + row + "col "+col); break;
            }
        } else if (row == 4) {
            switch (col) { // JAUNE PRESTIGE
                case 0: functionForButtonJP1(); System.out.println("row " + row + "col "+col); break;
                case 1: functionForButtonJP2(); System.out.println("row " + row + "col "+col); break;
                case 2: functionForButtonJP3(); System.out.println("row " + row + "col "+col); break;
                case 3: functionForButtonJP4(); System.out.println("row " + row + "col "+col); break;
                case 4: functionForButtonJP5(); System.out.println("row " + row + "col "+col); break;
                case 5: functionForButtonJP6(); System.out.println("row " + row + "col "+col); break;
            }
        } else if (row == 5) {
            switch (col) { // JAUNE CLASSQUE
                case 0: functionForButtonJC1(); System.out.println("row " + row + "col "+col); break;
                case 1: functionForButtonJC2(); System.out.println("row " + row + "col "+col); break;
                case 2: functionForButtonJC3(); System.out.println("row " + row + "col "+col); break;
                case 3: functionForButtonJC4(); System.out.println("row " + row + "col "+col); break;
                case 4: functionForButtonJC5(); System.out.println("row " + row + "col "+col); break;
                case 5: functionForButtonJC6(); System.out.println("row " + row + "col "+col); break;
            }
        } else if (row == 7) {
            switch (col) { // BLANC PRESTIGE
                case 0: functionForButtonBP1(); System.out.println("row " + row + "col "+col); break;
                case 1: functionForButtonBP2(); System.out.println("row " + row + "col "+col); break;
                case 2: functionForButtonBP3(); System.out.println("row " + row + "col "+col); break;
                case 3: functionForButtonBP4(); System.out.println("row " + row + "col "+col); break;
                case 4: functionForButtonBP5(); System.out.println("row " + row + "col "+col); break;
                case 5: functionForButtonBP6(); System.out.println("row " + row + "col "+col); break;
            }
        } else if (row == 8) {
                switch (col) { // BLANC CLASSQUE
                case 0: functionForButtonBC1(); System.out.println("row " + row + "col "+col); break;
                case 1: functionForButtonBC2(); System.out.println("row " + row + "col "+col); break;
                case 2: functionForButtonBC3(); System.out.println("row " + row + "col "+col); break;
                case 3: functionForButtonBC4(); System.out.println("row " + row + "col "+col); break;
                case 4: functionForButtonBC5(); System.out.println("row " + row + "col "+col); break;
                case 5: functionForButtonBC6(); System.out.println("row " + row + "col "+col); break;
            }
        }
    }

    private void functionForButtonRC1() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 1, 0);
    }

    private void functionForButtonRP1() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 1, 1);
    }

    private void functionForButtonRC2() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 2, 0);
    }

    private void functionForButtonRP2() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 2, 1);
    }

    private void functionForButtonRC3() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 3, 0);
    }

    private void functionForButtonRP3() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 3, 1);
    }

    private void functionForButtonRC4() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 4, 0);
    }

    private void functionForButtonRP4() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 4, 1);
    }

    private void functionForButtonRC5() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 5, 0);
    }

    private void functionForButtonRP5() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 5, 1);
    }

    private void functionForButtonRC6() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 6, 0);
    }

    private void functionForButtonRP6() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.ROUGE, 6, 1);
    }

    private void functionForButtonJC1() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 1, 0);
    }

    private void functionForButtonJP1() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 1, 1);
    }

    private void functionForButtonJC2() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 2, 0);
    }

    private void functionForButtonJP2() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 2, 1);
    }

    private void functionForButtonJC3() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 3, 0);
    }

    private void functionForButtonJP3() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 3, 1);
    }

    private void functionForButtonJC4() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 4, 0);
    }

    private void functionForButtonJP4() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 4, 1);
    }

    private void functionForButtonJC5() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 5, 0);
    }

    private void functionForButtonJP5() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 5, 1);
    }

    private void functionForButtonJC6() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 6, 0);
    }

    private void functionForButtonJP6() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.JAUNE, 6, 1);
    }

    private void functionForButtonBC1() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 1, 0);        
    }

    private void functionForButtonBP1() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 1, 1);
    }

    private void functionForButtonBC2() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 2, 0);
    }

    private void functionForButtonBP2() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 2, 1);
    }

    private void functionForButtonBC3() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 3, 0);
    }

    private void functionForButtonBP3() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 3, 1);
    }

    private void functionForButtonBC4() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 4, 0);
    }

    private void functionForButtonBP4() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 4, 1);
    }

    private void functionForButtonBC5() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 5, 0);
    }

    private void functionForButtonBP5() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 5, 1);
    }

    private void functionForButtonBC6() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 6, 0);
    }

    private void functionForButtonBP6() {
        partie.Construire(partie.getJoueur(0), partie.getJoueur(0).getFiche(), CouleurDe.BLANC, 6, 1);
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



    private JPanel createPeoplePanel() {
        JPanel newRectanglePanel = new JPanel();
        newRectanglePanel.setLayout(new BoxLayout(newRectanglePanel, BoxLayout.Y_AXIS));
        newRectanglePanel.setPreferredSize(new Dimension(700, 300));

        // Create the 3 lines with different colors
        Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
        //Score à edit selon la methode du code
        int[] scores = {0, 3, 18};

        // Load and resize images
        ImageIcon[] people = new ImageIcon[6];
        people[0] = new ImageIcon(new ImageIcon("./ressources/Portage/Plateau/GmcObtenu.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        people[1] = new ImageIcon(new ImageIcon("./ressources/Portage/GMC/GmcPeuple.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        people[2] = new ImageIcon(new ImageIcon("./ressources/Portage/Plateau/InfoObtenu.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        people[3] = new ImageIcon(new ImageIcon("./ressources/Portage/Info/InfoPeuple.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        people[4] = new ImageIcon(new ImageIcon("./ressources/Portage/Plateau/EdimObtenu.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        people[5] = new ImageIcon(new ImageIcon("./ressources/Portage/EDIM/EdimPeuple.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));


        for (int lineIndex = 0; lineIndex < colors.length; lineIndex++) {
            JPanel linePanel = new JPanel();
            linePanel.setPreferredSize(new Dimension(700, 100));
            linePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            linePanel.setOpaque(true);
            linePanel.setBackground(colors[lineIndex]);
            linePanel.setLayout(new GridLayout(1, 20));
    
            // Add images based on the scores
            int score = scores[lineIndex];
            for (int i = 0; i < 20; i++) {
                JLabel valueLabel;
                if (i < score) {
                    valueLabel = new JLabel(people[lineIndex * 2]); // Use different images for each line
                } else {
                    valueLabel = new JLabel(people[lineIndex * 2 + 1]); // Use different images for each line
                }
                valueLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                linePanel.add(valueLabel);
            }
    
            newRectanglePanel.add(linePanel);
        }
    
        return newRectanglePanel;
    }

    private JPanel createScoreCathedral() {
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
        JPanel newLeftRectanglePanel = createScoreCathedral();
        newAreaPanel.add(newLeftRectanglePanel, BorderLayout.WEST);

        // Add the new rectangle with 3 lines to the center of the new area
        JPanel newRectanglePanel = createPeoplePanel();
        newAreaPanel.add(newRectanglePanel, BorderLayout.CENTER);

        return newAreaPanel;
    }

    private void updateResourcesPanel(JPanel resourcesPanel, int blockIndex) {
        resourcesPanel.removeAll();

        // Load and resize images for resources
        ImageIcon[] resourceImages = new ImageIcon[6];
        resourceImages[0] = new ImageIcon(new ImageIcon("./ressources/Portage/GMC/RessourceGmc.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        resourceImages[1] = new ImageIcon(new ImageIcon("./ressources/Portage/GMC/RessourceGmcNonObtenue.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        resourceImages[2] = new ImageIcon(new ImageIcon("./ressources/Portage/Info/RessourceInfo.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        resourceImages[3] = new ImageIcon(new ImageIcon("./ressources/Portage/Info/RessourceInfoNonObtenue.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        resourceImages[4] = new ImageIcon(new ImageIcon("./ressources/Portage/EDIM/RessourceEdim.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        resourceImages[5] = new ImageIcon(new ImageIcon("./ressources/Portage/EDIM/RessourceEdimNonObtenue.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));

        char[] resources = controller.getResources().get(blockIndex);
        for (int i = 0; i < resources.length; i++) {
            char resource = resources[i];
            ImageIcon obtained;
            ImageIcon notObtained;
            
            if (blockIndex == 0) { // Red resources
                obtained = resourceImages[0];
                notObtained = resourceImages[1];
            } else if (blockIndex == 1) { // Yellow resources
                obtained = resourceImages[2];
                notObtained = resourceImages[3];
            } else { // White resources
                obtained = resourceImages[4];
                notObtained = resourceImages[5];
            }

            JLabel resourceLabel = new JLabel(resource == '1' ? obtained : notObtained, SwingConstants.CENTER);
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
            if ((button != null && button.getBackground().equals(diceColor))) {
                button.setEnabled(true);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        disablePlusButton(diceColor);
                    }
                });
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

    private void disablePlusButton(Color diceColor) {
        for (JPanel resourcesPanel : resourcesPanels) {
            for (Component component : resourcesPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    if (button.getText().equals("+") && button.getBackground().equals(diceColor)) {
                        button.setEnabled(false);
                    }
                }
            }
        }
    }
}