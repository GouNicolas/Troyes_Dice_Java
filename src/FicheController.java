import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FicheController {
    private boolean[][] matrix;
    private Set<String> smallCaseCoordinates;
    private List<char[]> resources;

    public FicheController(Fiche fiche) {
        int rows = 9;
        int cols = 6;
        matrix = new boolean[rows][cols];
        initialisermatrix(fiche);
        smallCaseCoordinates = new HashSet<>();
        // Add coordinates for small cases (example: between 0 and 1 of line 1)
        smallCaseCoordinates.add("1,0,1");
        smallCaseCoordinates.add("1,2,3");
        smallCaseCoordinates.add("1,4,5");
        smallCaseCoordinates.add("2,0,1");
        smallCaseCoordinates.add("2,4,5");
        smallCaseCoordinates.add("5,0,1");
        smallCaseCoordinates.add("5,2,3");
        smallCaseCoordinates.add("8,2,3");
        smallCaseCoordinates.add("8,4,5");

        // setValue(1, 0, true);
        // setValue(1, 1, true);

        // Initialize resources
        resources = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            char[] resource = new char[21];
            for (int j = 0; j < 21; j++) {
                if (j == 6 || j == 13 || j == 20) {
                    resource[j] = 'A';
                } else {
                    resource[j] = '0';
                }
            }
            resources.add(resource);
        }
    }

    public void initialisermatrix(Fiche fiche) {
        Couleur[] couleurs = {Couleur.ROUGE, Couleur.JAUNE, Couleur.BLANC};
        ArrayList<Batiment> listeBatiments = fiche.getListeBatiments();
        for (Couleur couleur : couleurs) {
            int rang = 0;
            for (Batiment batiment : listeBatiments) {
                if (couleur == Couleur.ROUGE) {
                    if (batiment instanceof BatimentPrestige && batiment.couleur == couleur) {
                        matrix[1][rang] = batiment.isConstruit();
                        rang++;
                    }
                    else if (batiment instanceof BatimentPeon && batiment.couleur == couleur) {
                        matrix[2][rang] = batiment.isConstruit();
                    }
                }

                else if (couleur == Couleur.JAUNE) {
                    if (batiment instanceof BatimentPrestige && batiment.couleur == couleur) {
                        matrix[4][rang] = batiment.isConstruit();
                        rang++;
                    }
                    else if (batiment instanceof BatimentPeon && batiment.couleur == couleur) {
                        matrix[5][rang] = batiment.isConstruit();
                    }
                }

                else if (couleur == Couleur.BLANC) {
                    if (batiment instanceof BatimentPrestige && batiment.couleur == couleur) {
                        matrix[7][rang] = batiment.isConstruit();
                        rang++;
                    }
                    else if (batiment instanceof BatimentPeon && batiment.couleur == couleur) {
                        matrix[8][rang] = batiment.isConstruit();
                    }
                }
            }
        }
    }

    public boolean getValue(int row, int col) {
        return matrix[row][col];
    }

    public void setValue(int row, int col, boolean value) {
        matrix[row][col] = value;
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(boolean[][] matrix) {
        this.matrix = matrix;
    }

    public boolean hasSmallCase(int row, int col1, int col2) {
        return smallCaseCoordinates.contains(row + "," + col1 + "," + col2);
    }

    public int getSmallCaseValue(int row, int col1, int col2) {
        if (getValue(row, col1) && getValue(row, col2)) {
            return 1;
        }
        return 0;
    }

    public List<char[]> getResources() {
        return resources;
    }
}