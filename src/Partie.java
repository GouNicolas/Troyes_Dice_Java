import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Partie {
    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    private Plateau plateau;
    private int jours;
    private FenetrePrincipale fenetrePrincipale;
    String currentCycle;

    public Partie() {
        this.plateau = new Plateau();
        this.jours = 1;
        this.currentCycle = "Jour";
    }

    public void ajouterJoueur(Joueur joueur) {
        listeJoueurs.add(joueur);
    }

    public void retirerJoueur(Joueur joueur) {
        listeJoueurs.remove(joueur);
    }

    public FenetrePrincipale getFenetrePrincipale() {
        return fenetrePrincipale;
    }

    public void setFenetrePrincipale(FenetrePrincipale fenetrePrincipale) {
        this.fenetrePrincipale = fenetrePrincipale;
    }

    public void tourDeJeu(Joueur joueur) {
        System.out.println("C'est le tour de " + joueur.getPseudo());
        joueur.getFiche().afficherFiche(joueur);
        plateau.afficherPlateau(currentCycle, jours);
    }

    public void passerTour() {
        // Passer le tour
    }

    public void prochainTour() {
        if (currentCycle.equals("Jour")) {
            currentCycle = "Nuit";
        } else {
            currentCycle = "Jour";
            jours += 1;
        }
    }
    
    public void startGame(FenetrePrincipale fenetrePrincipale) {
        setFenetrePrincipale(fenetrePrincipale);

        Map<Joueur, FicheGUI> ficheGUIMap = new HashMap<>();

        FicheGUI ficheGUI = fenetrePrincipale.getFicheGUIPanel(); 

        while (true) {
            for (Joueur joueur : listeJoueurs) {
                fenetrePrincipale.reload_fenetre(joueur);
                if (joueur.getFiche().getNombreHab(Couleur.BLANC) >= 3 && joueur.getFiche().getNombreHab(Couleur.JAUNE) >= 3 && joueur.getFiche().getNombreHab(Couleur.ROUGE) >= 3 && joueur.getNbBonusHabObtenus() < 1) {
                    joueur.ajouterRessource(Ressources.DRAPEAUX, 1);
                    joueur.ajouterRessource(Ressources.ARGENT, 1);
                    joueur.ajouterRessource(Ressources.CONNAISSANCE, 1);
                    joueur.setNbBonusHabObtenus(1);
                } else if (joueur.getFiche().getNombreHab(Couleur.BLANC) >= 6 && joueur.getFiche().getNombreHab(Couleur.JAUNE) >= 6 && joueur.getFiche().getNombreHab(Couleur.ROUGE) >= 6 && joueur.getNbBonusHabObtenus() < 2) {
                    ConstruireArbitraire(0, plateau, joueur, CouleurDe.TRANSPARENT);
                    joueur.setNbBonusHabObtenus(2);
                } else if (joueur.getFiche().getNombreHab(Couleur.BLANC) >= 11 && joueur.getFiche().getNombreHab(Couleur.JAUNE) >= 11 && joueur.getFiche().getNombreHab(Couleur.ROUGE) >= 11 && joueur.getNbBonusHabObtenus() < 3) {
                    ConstruireArbitraire(0, plateau, joueur, CouleurDe.TRANSPARENT);
                    joueur.setNbBonusHabObtenus(3);
                } else if (joueur.getFiche().getNombreHab(Couleur.BLANC) >=15 && !(joueur.isBonusPrestigeBlanc())) {
                    ConstruireArbitraire(1, plateau, joueur, CouleurDe.BLANC);
                    joueur.setBonusPrestigeBlanc(true);
                } else if (joueur.getFiche().getNombreHab(Couleur.JAUNE) >=15 && !(joueur.isBonusPrestigeJaune())) {
                    ConstruireArbitraire(1, plateau, joueur, CouleurDe.JAUNE);
                    joueur.setBonusPrestigeJaune(true);
                } else if (joueur.getFiche().getNombreHab(Couleur.ROUGE) >=15 && !(joueur.isBonusPrestigeRouge())) {
                    ConstruireArbitraire(1, plateau, joueur, CouleurDe.ROUGE);
                    joueur.setBonusPrestigeRouge(true);
                }
                //joueur.calculerScore();
                if (ficheGUI != null) {
                    ficheGUI.updateContent(joueur.getFiche(), joueur);
                    ficheGUI.revalidate();
                    ficheGUI.repaint();
                } else {
                    System.err.println("FicheGUI is null for " + joueur.getPseudo());
                }
                
                plateau.lancerDe();
                tourDeJeu(joueur);
                fenetrePrincipale.reload_fenetre(joueur);
                int choix = plateau.demanderChoixDe();

                while (plateau.checkRessourcesAchat(joueur, choix) == 1) {
                    choix = plateau.demanderChoixDe();
                }
                if (plateau.checkRessourcesAchat(joueur, choix) == 2) {
                    joueur.ajouterRessource(Ressources.DRAPEAUX, 1);
                    joueur.ajouterRessource(Ressources.ARGENT, 1);
                    joueur.ajouterRessource(Ressources.CONNAISSANCE, 1);
                    prochainTour();
                } else {
                    if (choix == 2) {
                        Ressources paiement = plateau.choixPaiementChoix2(joueur);
                        joueur.retirerRessource(paiement, 1);
                    } else if (choix == 3) {
                        joueur.retirerRessource(Ressources.ARGENT, 1);
                    } else if (choix == 4) {
                        joueur.retirerRessource(Ressources.ARGENT, 2);
                    }
                    demanderModifierDe(joueur, plateau.getListesDes().get(choix - 1));
                        int ChoixUtilisationDe = ChoixUtilisationDe();
                        if (ChoixUtilisationDe == 0) {
                            if (plateau.getListesDes().get(choix - 1).getCouleur()==CouleurDe.JAUNE){
                                joueur.ajouterRessource(Ressources.ARGENT, plateau.getListesDes().get(choix - 1).getValeur());
                            } else if (plateau.getListesDes().get(choix - 1).getCouleur()==CouleurDe.BLANC){
                                joueur.ajouterRessource(Ressources.CONNAISSANCE, plateau.getListesDes().get(choix - 1).getValeur());
                            } else if (plateau.getListesDes().get(choix - 1).getCouleur()==CouleurDe.ROUGE){
                                joueur.ajouterRessource(Ressources.DRAPEAUX, plateau.getListesDes().get(choix - 1).getValeur());
                            }
                        } else {
                        ConstruireChoix(choix, plateau, joueur);
                        }

                    //Fonction de destruction liée au dé noir
                    if (jours>=3){
                        int tmp=0;
                        for (De de : plateau.getListesDes()) {
                            if (de.getCouleur() == CouleurDe.NOIR) {
                                DestroyBuilding(joueur, plateau, de, tmp);
                            }
                            tmp++;
                        }
                    }
                }

                fenetrePrincipale.reload_fenetre(joueur);
                
                try {
                    Thread.sleep(1000); // 1 second delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (jours == 8 && currentCycle.equals("Nuit")) {
                for (Joueur joueur : listeJoueurs) {
                    int SC = joueur.calculerScore();
                    System.out.println("Score de " + joueur.getPseudo() + " : " + SC);
                }
                return;
            }
            if (currentCycle != "Nuit" || jours != 8){
                prochainTour();
            }
        }
    }
    
    

    public void demanderModifierDe(Joueur joueur, De de) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("etat actuel du de :\n");
        de.afficherDe();
        joueur.getFiche().afficherRessources(joueur);
        System.out.println("Voulez-vous modifier le dé ? (O/N)");
        String choix = scanner.nextLine();
        if (choix.equals("O")) {
            System.out.println("Voulez-vous modifier la couleur (C) ou la valeur (V) ou annuler (N) ?");
            choix = scanner.nextLine();
            if (choix.equalsIgnoreCase("C") && joueur.getInventaireRes().get(Ressources.CONNAISSANCE)>0) {
                demanderModifierCouleur(joueur, de);
            } else if (choix.equals("V") && joueur.getInventaireRes().get(Ressources.DRAPEAUX)>0) {
                demanderModifierValeur(joueur, de);
            } else if (choix.equals("N")) {
                demanderModifierDe(joueur, de);
            } else {
                System.out.println("Choix invalide. Veuillez choisir entre C, V et N.");
                demanderModifierDe(joueur, de);
            }
        }
    }

    private void demanderModifierCouleur(Joueur joueur, De de) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Modifier la couleur du De ? (B, J, R) ou anuler (A)");
        String couleur = scanner2.nextLine();
        if (couleur.equalsIgnoreCase("B")) {
            // Modifier la couleur du dé en blanc
            System.out.println("Couleur modifiée en Blanc");
            de.setCouleur(CouleurDe.BLANC);
            joueur.retirerRessource(Ressources.CONNAISSANCE, 1);
        } else if (couleur.equalsIgnoreCase("J")) {
            // Modifier la couleur du dé en jaune
            System.out.println("Couleur modifiée en Jaune");
            de.setCouleur(CouleurDe.JAUNE);
            joueur.retirerRessource(Ressources.CONNAISSANCE, 1);
        } else if (couleur.equalsIgnoreCase("R")) {
            // Modifier la couleur du dé en rouge
            System.out.println("Couleur modifiée en Rouge");
            de.setCouleur(CouleurDe.ROUGE);
            joueur.retirerRessource(Ressources.CONNAISSANCE, 1);
        } else if (couleur.equalsIgnoreCase("A")) {
            demanderModifierDe(joueur, de);
        } else {
            System.out.println("Couleur invalide. Veuillez choisir une couleur entre B, J, R et N.");
            demanderModifierCouleur(joueur, de);
        }
        demanderModifierDe(joueur, de);
    }

    private void demanderModifierValeur(Joueur joueur, De de) {
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Modifier la valeur du De ? (1-6) ou anuler la modification (0)");
        int valeur = scanner3.nextInt();
        if (valeur >= 1 && valeur <= 6 && valeur != de.getValeur()) {
            // Modifier la valeur du dé
            int difference = Math.abs(de.getValeur() - valeur);
            if (joueur.getInventaireRes().get(Ressources.DRAPEAUX) >= difference) {
                joueur.retirerRessource(Ressources.DRAPEAUX, difference);
                System.out.println("Valeur modifiée en " + valeur);
                de.setVal(valeur);
            } else {
                System.out.println("Vous n'avez pas assez de drapeaux pour effectuer cette modification.");
                demanderModifierValeur(joueur, de);
            }
        } else if (valeur == de.getValeur()) {
            System.out.println("La valeur est déjà la même. Veuillez choisir une autre valeur.");
        } else if (valeur == 0) {;
            demanderModifierDe(joueur, de);
        } else {
            System.out.println("Valeur invalide. Veuillez choisir un numéro entre 1 et 6.");
            demanderModifierValeur(joueur, de);
        }
        demanderModifierDe(joueur, de);
    }

    public void Construire(Joueur joueur, Fiche fiche, CouleurDe couleurDe, int index, int typeBatiment) {
            int place = 1;
            for (Batiment batiment : fiche.getListeBatiments()) {
                CouleurDe batimentCouleurDe = CouleurDe.fromCouleur(batiment.getCouleur());
                if (batimentCouleurDe == couleurDe) {
                    place++;
                }
                if (batimentCouleurDe == couleurDe && place == (((index) * 2 + typeBatiment)) && !batiment.isConstruit() && !batiment.isDetruit()) {
                    batiment.construire();
                    if (batiment.getCouleur()==Couleur.BLANC && place%2==1){//le batiment construit est une cathedrale
                        int nb_cat=fiche.getNBCathedrales();
                        fiche.ajouterCathedrale(index-1, nb_cat+1);
                    }
                    System.out.println("Batiment de couleur " + couleurDe + " construit." + index + " ou " + ((index - 1) * 2 + typeBatiment));
                    break;
                }
            }
            verificationBonusAdjacent(joueur, fiche, couleurDe, index, typeBatiment);
    }

    public void verificationBonusAdjacent(Joueur joueur, Fiche fiche, CouleurDe couleurDe, int index, int typeBatiment) {
        for (BonusAdjacent bonusAdjacent : fiche.getListeBonusAdjacent()) {
            List<Integer> liste = bonusAdjacent.getAdjacentIndexes();
            Couleur couleur = bonusAdjacent.getCouleur();
            int adjacentIndex1 = liste.get(0);
            int adjacentIndex2 = liste.get(1);
            if (couleur == Couleur.fromCouleurDe(couleurDe)) {
                if (liste.contains(((index - 1) * 2 + typeBatiment))) {
                    if (couleur == Couleur.JAUNE) {
                        adjacentIndex1 += 12;
                        adjacentIndex2 += 12;
                    }
                    else if (couleur == Couleur.BLANC) {
                        adjacentIndex1 += 24;
                        adjacentIndex2 += 24;
                    }

                    if (fiche.getListeBatiments().get(adjacentIndex1).isConstruit() && fiche.getListeBatiments().get(adjacentIndex2).isConstruit()) {
                            System.out.println("Bonus for batiment adjacent: " + bonusAdjacent.getBonus());
                            bonusAdjacent.AjouterBonusAdjacent(joueur, bonusAdjacent.getBonus());
                            break;
                    }
                }
            }
        }
    }

    public void ConstruireChoix(int choix, Plateau plateau, Joueur joueur) {
        De deChoisi = TransformationChoixToIndex(choix);
        int index = deChoisi.getValeur();
        CouleurDe couleurDe = deChoisi.getCouleur();
        Fiche fiche = joueur.getFiche();

        int typeBatiment = DemanderChoixTypeBatiment();

        System.out.println(choix + ": " + deChoisi.getValeur() + " (" + couleurDe + ")");
        
        Construire(joueur, fiche, couleurDe, index, typeBatiment);

        ajouterHabConstruction(joueur, couleurDe, index, typeBatiment);
    }


    public void ConstruireArbitraire(int typeBatiment, Plateau plateau, Joueur joueur, CouleurDe couleurPop) {
        if (typeBatiment == 0) {
            System.out.println("Bonus de population activé. Construisez un batiment classque de votre choix.");
        } else {
            System.out.println("Bonus de population activé. Construisez un batiment prestige de votre choix.");
        }
        Scanner scanner = new Scanner(System.in);
        Fiche fiche = joueur.getFiche();
        int place;
        Couleur couleurBatiment;
        String tmp;

        System.out.println("Choisissez la couleur : R/J/B\n");
        if (typeBatiment==1){
            System.out.println("La couleur ne doit pas être la même que la couleur de la population ayant atteint le palier de Bonus.");
        }
        tmp = scanner.nextLine();
        if (tmp.equals("R") && couleurPop != CouleurDe.ROUGE) {
            System.out.println("Vous avez choisi un batiment rouge.\n");
            couleurBatiment = Couleur.ROUGE;
        } else if (tmp.equals("J") && couleurPop != CouleurDe.JAUNE) {
            System.out.println("Vous avez choisi un batiment jaune.\n");
            couleurBatiment = Couleur.JAUNE;
        } else if (tmp.equals("B") && couleurPop != CouleurDe.BLANC) {
            System.out.println("Vous avez choisi un batiment blanc.\n");
            couleurBatiment = Couleur.BLANC;
        } else {
            System.out.println("Choix invalide.\n");
            ConstruireArbitraire(typeBatiment, plateau, joueur, couleurPop);
            return;
        }

        System.out.println("Choisissez la colonne : 1-6\n");
        tmp = scanner.nextLine();
        if (tmp.equals("1")) {
            place = 1;
        } else if (tmp.equals("2")) {
            place = 2;
        } else if (tmp.equals("3")) {
            place = 3;
        } else if (tmp.equals("4")) {
            place = 4;
        } else if (tmp.equals("5")) {
            place = 5;
        } else if (tmp.equals("6")) {
            place = 6;
        } else {
            System.out.println("Choix invalide.\n");
            ConstruireArbitraire(typeBatiment, plateau, joueur, couleurPop);
            return;
        }


        Construire(joueur, fiche, CouleurDe.fromCouleur(couleurBatiment), place, typeBatiment);

        CouleurDe couleurFinale;
        if (couleurBatiment == Couleur.ROUGE) {
            couleurFinale = CouleurDe.ROUGE;
        } else if (couleurBatiment == Couleur.JAUNE) {
            couleurFinale = CouleurDe.JAUNE;
        } else {
            couleurFinale = CouleurDe.BLANC;
        }

        ajouterHabConstruction(joueur, couleurFinale, place, typeBatiment);
    }

    public De TransformationChoixToIndex(int choix) {
        ArrayList<De> listesDes = plateau.getListesDes();
        return listesDes.get(choix - 1);
    }

    private int DemanderChoixTypeBatiment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choisissez le type de batiment que vous voulez construire : \nPrestige P, Classique C");
        String choix = scanner.nextLine();
        if (choix.equals("P")) {
            System.out.println("Vous avez choisi un batiment prestige.");
            
            return 1;
        } else if (choix.equals("C")) {
            System.out.println("Vous avez choisi un batiment classique ou peon.");
            
            return 0;
        } else {
            System.out.println("Choix invalide.");
            DemanderChoixTypeBatiment();
        }
        return -1;
    }

    public int ChoixUtilisationDe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez-vous utiliser un de pour construire un batiment ou obtenir des ressources? (B/R)");
        String choix = scanner.nextLine();
        if (choix.equals("B")) {
            System.out.println("Vous choisissez de construire un batiment.");
            return 1;
        } else if (choix.equals("R")) {
            System.out.println("Vous choisissez d'obtenir des ressources.");
            return 0;
        } else {
            System.out.println("Choix invalide. Veuillez choisir entre B et R.");
            return ChoixUtilisationDe();
        }
    }

    public void ajouterHabConstruction(Joueur joueur, CouleurDe couleurDe, int index, int typeBatiment) {
        Fiche fiche = joueur.getFiche();
        int place = 1;
        for (Map.Entry<Batiment, String> entry : fiche.getListeDesBonusBatiments().entrySet()) {
            Batiment batiment = entry.getKey();
            CouleurDe batimentCouleurDe = CouleurDe.fromCouleur(batiment.getCouleur());
            if (batimentCouleurDe == couleurDe) {
                place++;
            }
            if (batimentCouleurDe == couleurDe && place == (((index) * 2 + typeBatiment) - 1)) {
                System.out.println("Bonus for batiment: " + entry.getValue());
                if (entry.getValue().equals(" Cat ")) {
                    // Ajouter une cathédrale pour les points
                }
                else if (entry.getValue().equals("  B  ")) {
                    fiche.ajouterHab(Couleur.BLANC, 1);
                }
                else if (entry.getValue().equals(" B B ")) {
                    fiche.ajouterHab(Couleur.BLANC, 2);
                }

                else if (entry.getValue().equals("  R  ")) {
                    fiche.ajouterHab(Couleur.ROUGE, 1);
                }
                else if (entry.getValue().equals(" R R ")) {
                    fiche.ajouterHab(Couleur.ROUGE, 2);
                }

                else if (entry.getValue().equals("  J  ")) {
                    fiche.ajouterHab(Couleur.JAUNE, 1);
                }
                else if (entry.getValue().equals(" J J ")) {
                    fiche.ajouterHab(Couleur.JAUNE, 2);
                }

                else if (entry.getValue().equals("DDD*R")) {
                    ArrayList<De> listesDes = plateau.getListesDes();
                    for (De de : listesDes) {
                        if (de.getCouleur() == CouleurDe.ROUGE) {
                            joueur.ajouterRessource(Ressources.DRAPEAUX, 3);
                        }
                    }
                }
                else if (entry.getValue().equals("OOO*J")) {
                    ArrayList<De> listesDes = plateau.getListesDes();
                    for (De de : listesDes) {
                        if (de.getCouleur() == CouleurDe.JAUNE) {
                            joueur.ajouterRessource(Ressources.ARGENT, 3);
                        }
                    }
                }
                else if (entry.getValue().equals("CCC*B")) {
                    ArrayList<De> listesDes = plateau.getListesDes();
                    for (De de : listesDes) {
                        if (de.getCouleur() == CouleurDe.BLANC) {
                            joueur.ajouterRessource(Ressources.CONNAISSANCE, 3);
                        }
                    }

                }

                else if (entry.getValue().equals("R R*R")) {
                    ArrayList<De> listesDes = plateau.getListesDes();
                    for (De de : listesDes) {
                        if (de.getCouleur() == CouleurDe.ROUGE) {
                            fiche.ajouterHab(Couleur.ROUGE, 2);
                        }
                    }
                }

                else if (entry.getValue().equals("J J*J")) {
                    ArrayList<De> listesDes = plateau.getListesDes();
                    for (De de : listesDes) {
                        if (de.getCouleur() == CouleurDe.JAUNE) {
                            fiche.ajouterHab(Couleur.JAUNE, 2);
                        }
                    }
                }

                else if (entry.getValue().equals("B B*B")) {
                    ArrayList<De> listesDes = plateau.getListesDes();
                    for (De de : listesDes) {
                        if (de.getCouleur() == CouleurDe.BLANC) {
                            fiche.ajouterHab(Couleur.BLANC, 2);
                        }
                    }
                }

                break;
            }
        }
    }
    
    public Plateau getPlateau() {
        return plateau;
    }
    public int getJours() {
        return jours;
    }

    public void DestroyBuilding(Joueur joueur, Plateau plateau, De de, int placeListeDe) {
        int rang = de.getValeur();
        ArrayList<Batiment> listeBatiments = joueur.getFiche().getListeBatiments();
        int temp;
        if (currentCycle.equals("Jour")) {
            temp = (jours+placeListeDe)%9;
        } else {
            temp = (jours + 4+placeListeDe)%9;
        }
        int place=0;
        for (Batiment batiment : listeBatiments) {
            if (batiment.getCouleur() == plateau.getListeTuiles().get(temp).getCouleur()) {
                if ((place==(de.getValeur()-1)*2+1 || place==(de.getValeur()-1)*2) && !listeBatiments.get((de.getValeur()-1)*2+1).isConstruit() && !batiment.isConstruit()){
                    System.out.println("Destruction des batiment de couleur " + plateau.getListeTuiles().get(temp).getCouleur() + "de la " + de.getValeur() + "eme place "+ de.getValeur() + " ");
                    batiment.detruire();
                }
                place++;
            }
        }
    }
}
