import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class BonusAdjacent {
    private Couleur couleur;
    private int adjacentIndex1;
    private int adjacentIndex2;

    private Ressources_Bonus Bonus;

    public BonusAdjacent(Couleur couleur, int adjacentIndex1, int adjacentIndex2, Ressources_Bonus Bonus) {
        this.couleur = couleur;
        this.adjacentIndex1 = adjacentIndex1;
        this.adjacentIndex2 = adjacentIndex2;
        this.Bonus = Bonus;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public Ressources_Bonus getBonus() {
        return Bonus;
    }

    public List<Integer> getAdjacentIndexes() {
        return List.of(adjacentIndex1, adjacentIndex2);
    }

    public void AjouterBonusAdjacent(Joueur joueur, Ressources_Bonus bonus) {
        System.out.println("Ajout de bonus adjacent");
        if (bonus == Ressources_Bonus.UN_HAB_ROUGE) {
            joueur.getFiche().ajouterHab(Couleur.ROUGE, 1);
        }
        else if (bonus == Ressources_Bonus.UN_HAB_BLANC) {
            joueur.getFiche().ajouterHab(Couleur.BLANC, 1);
        }
        else if (bonus == Ressources_Bonus.UN_HAB_JAUNE) {
            joueur.getFiche().ajouterHab(Couleur.JAUNE, 1);
        }
        else if (bonus == Ressources_Bonus.DEUX_HAB_ROUGE) {
            joueur.getFiche().ajouterHab(Couleur.ROUGE, 2);
        }
        else if (bonus == Ressources_Bonus.DEUX_HAB_BLANC) {
            joueur.getFiche().ajouterHab(Couleur.BLANC, 2);
        }
        else if (bonus == Ressources_Bonus.DEUX_HAB_JAUNE) {
            joueur.getFiche().ajouterHab(Couleur.JAUNE, 2);
        }
        else if (bonus == Ressources_Bonus.TROIS_ARGENTS) {
            joueur.ajouterRessource(Ressources.ARGENT, 3);
        }
        else if (bonus == Ressources_Bonus.TROIS_DRAPEAUX) {
            joueur.ajouterRessource(Ressources.DRAPEAUX, 3);
        }
        else if (bonus == Ressources_Bonus.TROIS_CONNAISSANCES) {
            joueur.ajouterRessource(Ressources.CONNAISSANCE, 3);
        }

    }
}
