import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

class Fiche {
    private HashMap<Couleur, Integer> listeHab = new HashMap<>();
    private ArrayList<Batiment> listeBatiments = new ArrayList<>();
    private LinkedHashMap<Batiment, String> listeDesBonusBatiments = new LinkedHashMap<>();
    private ArrayList<BonusAdjacent> listeBonusAdjacent = new ArrayList<>();
    private List<Integer> listeCathedrales; //liste toutes les cathédrales et leur ordre de construction. tout est initialisé a 0.

    public Fiche() {
        listeHab.put(Couleur.ROUGE, 0);
        listeHab.put(Couleur.JAUNE, 0);
        listeHab.put(Couleur.BLANC,0);
        listeCathedrales = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listeCathedrales.add(0);
        }

        initialiserBatiments();
        initailiserDesBonusBatimentsAdjacent();
    }

    int getNBCathedrales(){
        int nbCathedrales = 0;
        for (int i = 0; i < 6; i++) {
            if (listeCathedrales.get(i) !=0) {
                nbCathedrales++;
            }
        }
        return nbCathedrales;
    }

    public void ajouterCathedrale(int place, int quantieme){
        listeCathedrales.set(place, quantieme);
        // System.out.println("Cathédrale placée en position " + place + "de la liste, c'est la " + quantieme+"eme");
        // System.out.println("Liste des cathédrales : " + listeCathedrales);
    }

    public List<Integer> getListeCathedrales() {
        return listeCathedrales;
    } 

    public ArrayList<Batiment> getListeBatiments() {
        return listeBatiments;
    }

    public HashMap<Couleur, Integer> getListeHab() {
        return listeHab;
    }

    public int getNombreHab() {
        int total = 0;
        for (Map.Entry<Couleur, Integer> entry : listeHab.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    public int getNombreHab(Couleur couleur) {
        return listeHab.getOrDefault(couleur, 0);
    }

    public void ajouterHab(Couleur couleur, int nombre) {
        listeHab.put(couleur, listeHab.getOrDefault(couleur, 0) + nombre);
    }

    public LinkedHashMap<Batiment, String> getListeDesBonusBatiments() {
        return listeDesBonusBatiments;
    }

    public ArrayList<BonusAdjacent> getListeBonusAdjacent() {
        return listeBonusAdjacent;
    }

    private void initailiserDesBonusBatimentsAdjacent() {
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.ROUGE, 0, 2, Ressources_Bonus.DEUX_HAB_ROUGE));
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.ROUGE, 1, 3, Ressources_Bonus.UN_HAB_ROUGE));
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.ROUGE, 5, 7, Ressources_Bonus.UN_HAB_JAUNE));
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.ROUGE, 8, 10, Ressources_Bonus.TROIS_DRAPEAUX));
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.ROUGE, 9, 11, Ressources_Bonus.UN_HAB_BLANC));

        listeBonusAdjacent.add(new BonusAdjacent(Couleur.JAUNE, 0, 2, Ressources_Bonus.TROIS_ARGENTS));
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.JAUNE, 4, 6, Ressources_Bonus.DEUX_HAB_JAUNE));

        listeBonusAdjacent.add(new BonusAdjacent(Couleur.BLANC, 4, 6, Ressources_Bonus.TROIS_CONNAISSANCES));
        listeBonusAdjacent.add(new BonusAdjacent(Couleur.BLANC, 8, 10, Ressources_Bonus.DEUX_HAB_BLANC));
    }

    private void initialiserBatiments() {
        Couleur[] couleurs = {Couleur.ROUGE, Couleur.JAUNE, Couleur.BLANC};
        for (Couleur couleur : couleurs) {
            for (int i = 0; i < 6; i++) {
                BatimentPeon peon = new BatimentPeon(couleur);
                BatimentPrestige prestige = new BatimentPrestige(couleur);
                listeBatiments.add(peon);
                listeBatiments.add(prestige);

                if (couleur == Couleur.ROUGE) {
                    if (i == 0 || i == 1) {
                        listeDesBonusBatiments.put(prestige, "  R  ");
                    }
                    else if (i == 2 || i == 3) {
                        listeDesBonusBatiments.put(prestige, "  J  ");
                    }
                    else if (i == 4 || i == 5) {
                        listeDesBonusBatiments.put(prestige, "  B  ");
                    }
                    listeDesBonusBatiments.put(peon, " R R ");
                }

                else if (couleur == Couleur.JAUNE) {
                    if (i == 0) {
                        listeDesBonusBatiments.put(prestige, "DDD*R");
                    }
                    else if (i == 1) {
                        listeDesBonusBatiments.put(prestige, "R R*R");
                    }
                    else if (i == 2) {
                        listeDesBonusBatiments.put(prestige, "OOO*J");
                    }
                    else if (i == 3) {
                        listeDesBonusBatiments.put(prestige, "J J*J");
                    }
                    else if (i == 4) {
                        listeDesBonusBatiments.put(prestige, "CCC*B");
                    }
                    else {
                        listeDesBonusBatiments.put(prestige, "B B*B");
                    }
                    listeDesBonusBatiments.put(peon, " J J ");
                }

                else if (couleur == Couleur.BLANC) {
                    if (i == 0) {
                        listeDesBonusBatiments.put(prestige, " Cat ");
                    }
                    else if (i == 1) {
                        listeDesBonusBatiments.put(prestige, " Cat ");
                    }
                    else if (i == 2) {
                        listeDesBonusBatiments.put(prestige, " Cat ");
                    }
                    else if (i == 3) {
                        listeDesBonusBatiments.put(prestige, " Cat ");
                    }
                    else if (i == 4) {
                        listeDesBonusBatiments.put(prestige, " Cat ");
                    }
                    else {
                        listeDesBonusBatiments.put(prestige, " Cat ");
                    }
                    listeDesBonusBatiments.put(peon, " B B ");
                }
            }
        }
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
                    if (batiment.isDetruit()) {
                        System.out.print("  X  |");
                    }
                    else if (batiment.construit) {
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
            System.out.print("|");
            for (Map.Entry<Batiment, String> entry : listeDesBonusBatiments.entrySet()) {
                if (entry.getKey().couleur == couleur && entry.getKey() instanceof BatimentPrestige) {
                    System.out.print(entry.getValue() + "|");
                }
            }

            System.out.println();
            
            System.out.println("------------------------------------------------");
            rang = 1;
            // Second layer for BatimentPeon
            System.out.print("|");
            for (Batiment batiments : listeBatiments) {
                if (batiments instanceof BatimentPeon && batiments.couleur == couleur) {
                    if (batiments.isDetruit()) {
                        System.out.print("  X  |");
                    }
                    else if (batiments.construit) {
                        if (listeBatiments.get(rang).construit) {
                            System.out.print("[(2)]|");
                        }
                        else {
                            System.out.print(" (2) |");
                        }
                    } else {
                        if (listeBatiments.get(rang).construit) {
                            System.out.print("[ 2 ]|");
                        }
                        else {
                            System.out.print("  2  |");
                        }
                    }
                    rang = rang + 2;
                }
            }
            System.out.println();

            System.out.print("|");
            for (Map.Entry<Batiment, String> entry : listeDesBonusBatiments.entrySet()) {
                if (entry.getKey().couleur == couleur && entry.getKey() instanceof BatimentPeon) {
                    System.out.print(entry.getValue() + "|");
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
    
    
    
    public void afficherRessources(Joueur joueur) {
        for (Map.Entry<Ressources, Integer> entry : joueur.getInventaireRes().entrySet()) {
            System.out.println("| " + entry.getKey() + ": " + entry.getValue());
        }
    }

}
