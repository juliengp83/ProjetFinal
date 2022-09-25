# Journal de Bord

## 08 aout 2022
* Nous avons décidé des rôles de chacun dans l'équipe Agile.
* Mise en place du dépôt GitHub 
* Nous avons déterminé le guide de styles qui sera utilisé tout au long de notre projet.

## 15 aout 2022
* Séance utilisée pour faire le résumé d'équipe collectivement en prenant les points saillants de nos résumés
  individuels.
* Nous avons commencé à nous familiariser avec Trello
* Suivre la formation Git sur OpenClassrooms

## 18 aout 2022
* Définir le backlog du produit et du sprint 1 sur Trello
* Assister Oleksandr dans la mise en place de ses logiciels (trello, git, discord)

## 20 aout 2022
* J'ai regardé quelques vidéos sur Spring pour en comprendre quelques concepts essentiels
* Commencé la lecture du livre Spring in Action (6th edition, 2022) par Craig Walls

## 25 aout 2022
* Migrer vers une version `Mavenisée` de notre projet.
* Création des classes de lecture, traitement et écriture afin de cibler l'ensemble du projet.
* Nous nous sommes donné comme objectif de préparer des feuilles à tester pour la prochaine séance.

## 29 aout 2022
* Écriture des javadoc
* Écriture du cahier des charges
* Correction des messages d'erreurs
* Écriture du module d'écriture du logiciel
* Adapter le main à une utilisation avec les arguments en ligne de commandes pour utilisation avec notre jar
* Ajouter la configuration de l'assembleur Maven pour intégrer les dépendances de notre projet dans le .jar compilé

## 1er septembre
* Compilation de la version 'finale' de notre jar et ajout dans le dossier Release.
* Correction d'erreurs dans les tests, gestion des exceptions nom du fichier
* Merge la branche de development

## 5 septembre
* Participation au scrum poker avec l'équipe
* Présentation de livres et vidéos sur Spring à l'équipe
* Définition du backlog du sprint 2 (prioritisation, évaluation de temps)

## 5-11 septembre
* Lecture du livre Spring in Action (6th Edition)
* Lecture du livre Spring Security in Action

## 12 septembre
* Intégration de Spring Boot à notre application principale
* Tentative d'appliquer Spring Security à notre projet pour l'authentification des users à l'aide d'une base de donnée 
  Oracle
## 15 septembre 2022
* Puisque Spring Security complique beaucoup les choses et que je ne veux pas affecter le fonctionnement de  certaines 
parties du projets j'ai opté pour un simple cryptage AES pour la conservation des mots de passes. Bien entendu, puisque notre
application n'est pas réellement déployée et afin de faciliter notre développement nous n'utilisons pas encore https donc les données 
'seraient envoyées en clair'. [Lien vers article permettant de migrer vers https](https://mkyong.com/spring-boot/spring-boot-ssl-https-examples/)
* Je suis conscient que d'avoir hardcoded notre clé secrète dans le code source n'est pas une bonne pratique.

## 19 septembre 2022
* Création du controlleur JsonController.java qui sera utilisé pour recevoir les informations par une requête AJAX recueillies dans notre formulaire dynamique, 
les envoyer à la validation et renvoyer une réponse à la page appelante afin qu'elle puisse afficher les informations.

## 22 septembre 2022
* Refactorisation du JsonController et du LecteurJson.java et ÉcritureJson.java afin d'améliorer la lisibilité de celui-ci
* Création de l'entité FeuilleTemps afin de permettre 
* Ajout des méthodes permettant la persistence de celles-ci.

## 25 septembre 2022
* Assister Émile avec le côté backend de la page gestionnaire
* Travailler sur le cahier des charges
