# Normes de programmation

## Conventions de nommage des variables et langue
*Privilégier l'usage du français dans la documentation, les commentaires les noms de variables et méthodes sauf
pour les getters et les setters
*Les variables seront écrites en snake case : ex. `une_variable`
*Les constantes seront écrites en snake case majuscule : ex. `MA_CONSTANTE`
*Les fonctions et méthodes seront écrites en camel case : ex. `maFonction()`
*Les classes seront écrites en camel case commençant par une  majuscule : ex. `MaClasse()`
*Utiliser des noms significatifs qui ne se ressemblent pas trop et éviter les noms trop longs
*Signifier l'utilisation d'un booléen par le préfixe est (ex. estValide)
*Éviter l'utilisation des abbréviations
*Éviter d'ajouter le préfixe liste et privilégier le pluriel du type d'objet (ex. employes plutôt que listeEmployes)

## Espaces, indentation et accolades
*Les tabs seront représentés par 4 espaces (Respect de l'indentation et imbrication standard par défaut)
*Un espace après la signature des fonctions et classes ex. `int sum(int a, int b) {}`
*L'accolade ouvrante sur la même ligne que la signature
*Respecter l'indentation standard
*Si un if ou for est d'une ligne ne pas utiliser les accolades

## Commentaires, documentation et test unitaire
*Commentaires le plus allégé possible (éviter les commentaires parasites)
*Commentaire à une ligne en haut d'un bloc de code à expliquer
*Définition des méthodes à l'aide du format Javadoc afin de les générer à la fin du projet
*Indiquer dans le Javadoc si nous avons réaliser les test unitaires
*Utiliser TODO: afin de prendre avantage du parser de Warning de Java

## Format des blocs, lignes et modularité
*Éviter l'usage de variables globales
*Chaque fonction doit représenter une seule action
*Fonctions ou blocs d'environ 60 lignes maximum (préférer la modularité)
*Largeur des lignes maximum 120 colonnes                                                

## Structure de contrôles et boucles
*Utilisation d'un switch case lorsqu'on a beaucoup de branchements (ex. if else)
*Prioriser des forEach ou enhanced for loops (et dans le cas échéant utiliser i,j,k)
*Utilisation judicieuse des while et for selon la connaissance de la longueur de la structure de données
*Utilisation des booléens dans le bon sens et sans == false ou true (ex: estValide ou !estValide)