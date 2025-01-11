import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.awt.Color;

public class FicheController {
    private boolean[][] matrix;
    private Set<String> smallCaseCoordinates;
    private List<char[]> resources;
    
    public FicheController(Fiche fiche, Joueur joueur) {
        int rows = 9;
        int cols = 6;
        matrix = new boolean[rows][cols];
        smallCaseCoordinates = new HashSet<>();
        resources = new ArrayList<>(); // Initialize the resources list
        
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
        
        initialisermatrix(fiche);
        initialiserResources(joueur);
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
    
    public void initialiserResources(Joueur joueur) {
        int nb_RessD = joueur.getInventaireRes().get(Ressources.DRAPEAUX);
        int nb_RessO = joueur.getInventaireRes().get(Ressources.ARGENT);
        int nb_RessC = joueur.getInventaireRes().get(Ressources.CONNAISSANCE);
        
        int nb_HistoriqueD = joueur.getHistoriqueRes().get(Ressources.DRAPEAUX);
        int nb_HistoriqueO = joueur.getHistoriqueRes().get(Ressources.ARGENT);
        int nb_HistoriqueC = joueur.getHistoriqueRes().get(Ressources.CONNAISSANCE);

        nb_HistoriqueD -= nb_RessD;
        nb_HistoriqueO -= nb_RessO;
        nb_HistoriqueC -= nb_RessC;
        
        resources.clear();
        
        for (int i = 0; i < 3; i++) {
            char[] resource = new char[21];
            for (int j = 0; j < 21; j++) {
                if (i == 0) {
                    if (j == 6 || j == 13 || j == 20) {
                        resource[j] = nb_RessD >= 0 ? '1' : '0';
                    } else if (nb_HistoriqueD > 0) {
                        resource[j] = '1';
                        nb_HistoriqueD--;
                    } else if (nb_RessD > 0) {
                        resource[j] = '1';
                        nb_RessD--;
                    } else {
                        resource[j] = '0';
                        nb_RessD--;
                    }
                } else if (i == 1) {
                    if (j == 6 || j == 13 || j == 20) {
                        resource[j] = nb_RessO >= 0 ? '1' : '0';
                    } else if (nb_HistoriqueO > 0) {
                        resource[j] = '1';
                        nb_HistoriqueO--;
                    } else if (nb_RessO > 0) {
                        resource[j] = '1';
                        nb_RessO--;
                    } else {
                        resource[j] = '0';
                        nb_RessO--;
                    }
                } else if (i == 2) {
                    if (j == 6 || j == 13 || j == 20) {
                        resource[j] = nb_RessC >= 0 ? '1' : '0';
                    } else if (nb_HistoriqueC > 0) {
                        resource[j] = '1';
                        nb_HistoriqueC--;
                    } else if (nb_RessC > 0) {
                        resource[j] = '1';
                        nb_RessC--;
                    } else {
                        resource[j] = '0';
                        nb_RessC--;
                    }
                }
            }
            resources.add(resource);
        }
    }

    public void ajouterRessource(Color color) {
        int index = -1;
        if (Color.RED.equals(color)) {
            index = 0;
        } else if (Color.YELLOW.equals(color)) {
            index = 1;
        } else if (Color.WHITE.equals(color)) {
            index = 2;
        }
    
        if (index != -1) {
            char[] resourceArray = resources.get(index);
            for (int i = 0; i < resourceArray.length; i++) {
                if (resourceArray[i] == '0') {
                    resourceArray[i] = '1';
                    break;
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