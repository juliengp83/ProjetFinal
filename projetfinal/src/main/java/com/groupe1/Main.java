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
        if(args.length == 0) {
            // String nom_fichier = args[1];
            
            ArrayList<EmployeProjet> employe_projets = LecteurJson.lireFichier("feuilletemps.json");

            //System.out.println(employe_projets);

            Resultat resultat_traitement = TraitementFeuille.traitement(employe_projets);
            boolean resultat_est_ecrit = EcritureJson.ecrireFichier(resultat_traitement, "result.json");
        }
    }

}
