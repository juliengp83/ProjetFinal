package com.groupe1;

import java.util.ArrayList;

import com.groupe1.modeles.EmployeProjet;
import com.groupe1.modeles.Regle;
import com.groupe1.modeles.Resultat;
import com.groupe1.utils.EcritureJson;
import com.groupe1.utils.LecteurJson;
import com.groupe1.utils.TraitementFeuille;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            // String nom_fichier = args[1];

            ArrayList<EmployeProjet> employe_projets = LecteurJson.lireFichier("feuilletemps.json");

            System.out.println("Règle 1: " + TraitementFeuille.testRegle1(employe_projets).isEst_respectee()
                    + " message: " + TraitementFeuille.testRegle1(employe_projets).getMessage());

            System.out.println("Règle 2: " + TraitementFeuille.testRegle2(employe_projets).isEst_respectee()
                    + " message: " + TraitementFeuille.testRegle2(employe_projets).getMessage());

            System.out.println("Règle 3: " + TraitementFeuille.testRegle3(employe_projets).isEst_respectee()
                    + " message: " + TraitementFeuille.testRegle3(employe_projets).getMessage());

            System.out.println("Règle 4: " + TraitementFeuille.testRegle4(employe_projets).isEst_respectee()
                    + " message: " + TraitementFeuille.testRegle4(employe_projets).getMessage());

            System.out.println("Règle 5: " + TraitementFeuille.testRegle5(employe_projets).isEst_respectee()
                    + " message: " + TraitementFeuille.testRegle5(employe_projets).getMessage());

            Resultat resultat_traitement = TraitementFeuille.traitement(employe_projets);

            boolean resultatEstEcrit = EcritureJson.ecrireFichier(resultat_traitement, "result.json");
        }
    }

}
