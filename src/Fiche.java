import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

class Fiche {
    private HashMap<Couleur, Integer> listeHab = new HashMap<>();
    private ArrayList<Batiment> listeBatiments = new ArrayList<>();
    private ArrayList<String> listeDesBonusBatiments = new ArrayList<>();
    private ArrayList<int[]> listeEmplacementsDetruit = new ArrayList<>();

    public Fiche() {
        listeHab.put(Couleur.ROUGE, 0);
        listeHab.put(Couleur.JAUNE, 0);
        listeHab.put(Couleur.BLANC, 0);

        initialiserBatiments();
    }

    public ArrayList<Batiment> getListeBatiments() {
        return listeBatiments;
    }

    public HashMap<Couleur, Integer> getListeHab() {
        return listeHab;
    }

    public void ajouterHab(Couleur couleur, int nombre) {
        listeHab.put(couleur, listeHab.getOrDefault(couleur, 0) + nombre);
    }

    private void initialiserBatiments() {
        Couleur[] couleurs = {Couleur.ROUGE, Couleur.JAUNE, Couleur.BLANC};
        for (Couleur couleur : couleurs) {
            for (int i = 0; i < 6; i++) {
                listeBatiments.add(new BatimentPeon(couleur));
                listeBatiments.add(new BatimentPrestige(couleur));
            }
        }
    }

    public void ajouterEmplacementDetruit(int x, int y) {
        listeEmplacementsDetruit.add(new int[]{x, y});
    }

    public void afficherFiche(Joueur joueur) {
        System.out.println("================================================");
        System.out.println("|                     FICHE                    |");
        System.out.println("================================================");

        Couleur[] couleurs = {Couleur.ROUGE, Couleur.JAUNE, Couleur.BLANC};
        for (Couleur couleur : couleurs) {
            System.out.printf("|             |      %-8s     |              |\n", couleur);
            System.out.println("------------------------------------------------");

            // First layer for BatimentPrestige
            System.out.print("|");
            int rang = 1;
            for (Batiment batiment : listeBatiments) {
                if (batiment instanceof BatimentPrestige && batiment.couleur == couleur) {
                    if (batiment.construit) {
                        if (listeBatiments.get(rang).construit) {
                            System.out.print("[(1)]|");
                        }
                        else {
                            System.out.print(" (1) |");
                        }
                    } else {
                        if (listeBatiments.get(rang).construit) {
                            System.out.print("[ 1 ]|");
                        }
                        else {
                            System.out.print("  1  |");
                        }
                    }
                    rang = rang + 2;
                }
            }
            System.out.println();
            
            System.out.println("------------------------------------------------");
            rang = 1;
            // Second layer for BatimentPeon
            System.out.print("|");
            for (Batiment batiment : listeBatiments) {
                rang++;
                if (batiment instanceof BatimentPeon && batiment.couleur == couleur) {
                    if (batiment.construit) {
                        if (listeBatiments.get(rang - 1).construit) {
                            System.out.print("[(2)]|");
                        }
                        else {
                            System.out.print(" (2) |");
                        }
                    } else {
                        if (listeBatiments.get(rang - 1).construit) {
                            System.out.print("[ 2 ]|");
                        }
                        else {
                            System.out.print("  2  |");
                        }
                    }
                }
            }
            System.out.println();
            
        }

        System.out.println("================================================");
        System.out.println("|                INVENTAIRE RESSOURCES          |");
        System.out.println("================================================");
        for (Map.Entry<Ressources, Integer> entry : joueur.getInventaireRes().entrySet()) {
            System.out.println("| " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("================================================");
        System.out.println("|                HABITANTS                      |");
        System.out.println("================================================");
        for (Map.Entry<Couleur, Integer> entry : listeHab.entrySet()) {
            System.out.println("| " + entry.getKey() + ": " + entry.getValue() + " |");
        }
    }
}