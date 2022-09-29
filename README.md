# ProjetFinal
Projet de session du cours 420-B34-RO - Développement des applications informatiques

## Système de validation de feuilles de temps
Nous avons été mandatés par une entreprise pour mettre en place en un logiciel qui permettra la validation des feuilles
de temps des employés au regard de certaines règles appliquées dans l'entreprise. Nous avons choisi d'utiliser le framework
Spring Boot pour la réalisation du site web. Nous utiliserons donc le templating engine Thymeleaf, Spring Data JPA pour effectuer la
persistence des données et Semantic UI (un framework CSS) pour styliser notre interface utilisateur.


## Instructions générales pour utiliser notre logiciel
Nous recommandons d'installer le pack d'extension Spring Boot Extension Pack contenant plusieurs outils facilitant le développement
et le lancement d'application Spring Boot. Il est nécessaire d'avoir au préalable une base de données Oracle 11 ou plus, aucune
tables n'ont à être créés manuellement étant donné que Spring Data JPA les générera. Notre CommandLineRunner insérera des données
afin de permettre l'utilisation du logiciel. Pour permettre la connection à la base de données vous devrez insérer l'url correspondant
à la votre ainsi que le compte utilisateur. Ces propriétés se trouvent dans le fichier `application.yml` sauvegardée au chemin suivant : 
`Dossier_du_repo/projetfinal/src/main/resources`.
```
spring:
  jpa:
    database-platform: org.hibernate.dialect.Oracle9Dialect
    hibernate:
      ddl-auto: create

  datasource:
      url: jdbc:oracle:thin:@//localhost:1521/xe
      username: HR 
      password: HR 
      driver-class-name: oracle.jdbc.driver.OracleDriver

logging:
    level:
      '[org.hibernate.SQL]': DEBUG
      '[orb.hibernate.type.descriptor.sql.BasicBinder]': TRACE
```

La propriété `spring.jpa.hibernate.ddl-auto` doit être à `create` afin de créer les tables mais cette dernière les regénérera
identiquement à chaque démarrage de l'application - les données ne seront donc pas persistées réellement tant que nous n'aurons
pas changé cette propriété pour `update` et que nous n'aurons pas mis en commentaire le CommandLineRunner présent dans le main
de l'application : FeuilletempsApplication.java. Si nous omettons cette dernière action toute les entités de base seront présentes
deux fois dans la base de données donc c'est très important.