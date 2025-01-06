public class PlateauController {
    private PlateauGUI gui;
    private Partie partie;
    private Plateau plateau;
        private PlateauGUI plateauGUI;
    
        public PlateauController(Plateau plateau, Partie partie, PlateauGUI plateauGUI) {
            this.plateau = plateau;
            this.partie = partie;
            this.plateauGUI = plateauGUI;
    }

    // Method to trigger repaint
    public void updateGUI() {
        plateauGUI.repaint();
    }
    public void handleTuileSelection(int index) {

        // Implement the method logic here

        System.out.println("Tuile selected: " + index);

    }
    public void setGUI(PlateauGUI gui) {
        this.gui = gui;
    }
}
