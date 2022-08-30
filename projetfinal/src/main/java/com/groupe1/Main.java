package com.groupe1;

import java.util.ArrayList;

import com.groupe1.modeles.EmployeProjet;
import com.groupe1.modeles.Resultat;
import com.groupe1.utils.EcritureJson;
import com.groupe1.utils.LecteurJson;
import com.groupe1.utils.TraitementFeuille;

public class Main 
{
    public static void main( String[] args )
    {
        if(args.length == 2) {
            String nom_fichier_entree = args[0];
            String nom_fichier_sortie = args[1];
            
            ArrayList<EmployeProjet> employe_projets = LecteurJson.lireFichier(nom_fichier_entree);

            Resultat resultat_traitement = TraitementFeuille.traitement(employe_projets);
            EcritureJson.ecrireFichier(resultat_traitement, nom_fichier_sortie);

        } else if(args.length < 2) {
            System.out.println("Vous n'avez pas entré assez d'arguments lors de l'exécution du programme.\n" +
            "Voici un exemple: java -jar leNomDuFichier.jar <fichier entrée> <fichier sortie>.");
        } else {
            System.out.println("Vous avez entré plus de 2 arguments lors de l'exécution du programme.\n" +
            "Voici un exemple: java -jar leNomDuFichier.jar <fichier entrée> <fichier sortie>.");
        }
    }

}
