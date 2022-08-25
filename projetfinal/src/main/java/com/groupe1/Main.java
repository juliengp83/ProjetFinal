package com.groupe1;

import java.util.ArrayList;

import com.groupe1.modeles.EmployeProjet;
import com.groupe1.utils.LecteurJson;;

public class Main 
{
    public static void main( String[] args )
    {
        if(args.length == 0){
            // String nom_fichier = args[1];
            
            ArrayList<EmployeProjet> employe_projets = LecteurJson.lireFichier("feuilletemps.json");
        }
    }

}
