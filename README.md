Collecting workspace information

# Troyes_Dice_Java

## Description
Troyes Dice est une adaptation numérique du jeu de société en Java. Le jeu combine gestion de dés, construction de bâtiments et développement de population dans un environnement médiéval.

## Fonctionnalités

- Système de gestion de dés avec modifications possibles
WARNING adaptation UTBM
- Gestion des ressources (Connaissance, Argent, Drapeaux)
- Système de construction de bâtiments
- Tableau de progression du joueur
- Cycle Jour/Nuit
- Calcul automatique des scores

## Interface du Jeu

### Plateau Principal (

PlateauGUI

)
- Affiche le plateau de jeu avec les tuiles interactives
- Visualisation des dés disponibles
- Gestion de la sélection des tuiles

[Insérer capture d'écran du plateau principal]

### Fiche Joueur (

FicheGUI

)
- Suivi de la progression du joueur
- Affichage des ressources
- Visualisation des bâtiments construits
- Indicateurs de population

[Insérer capture d'écran de la fiche joueur]

### Modification des Dés (

ChangementDeGUI

)
- Interface de modification des dés
- Options de changement de valeur et de couleur
- Visualisation des coûts de modification

[Insérer capture d'écran du panneau de modification des dés]

## Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/votre-username/Troyes_Dice_Java.git
```

2. Compiler le projet :
```bash
cd Troyes_Dice_Java
javac src/*.java
```

3. Lancer le jeu :
```bash
java -cp src Main
```

## Comment Jouer

1. **Début de Partie**
   - Chaque joueur commence avec des ressources de base
   - Le plateau est initialisé avec des tuiles aléatoires

2. **Tour de Jeu**
   - Lancer les dés
   - Choisir un dé et payer son coût
   - Modifier le dé (optionnel)
   - Utiliser le dé pour construire ou gagner des ressources

3. **Objectifs**
   - Construire des bâtiments
   - Développer sa population
   - Gérer ses ressources
   - Obtenir le meilleur score

## Structure du Projet

- 

Main.java

 : Point d'entrée du programme
- 

Partie.java

 : Logique principale du jeu
- 


## Licence

Ce projet est sous licence MIT. Voir le fichier 

LICENSE

 pour plus de détails.

## Auteurs

GouNicolas[]

## Remarques

- Version minimum de Java requise : JDK 8
- Résolution d'écran minimale recommandée : 1024x768