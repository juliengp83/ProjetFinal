package com.groupe1.utils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.groupe1.modeles.EmployeProjet;

public class LecteurJson {

    /** Cette fonction permet la lecture d'un fichier JSON représentant la feuille de temps hebdomadaire complète
     *  d'un employé.
     * 
     * @param nom_fichier Le nom complet du fichier JSON contenant la feuille de temps dans le format structuré
     * demandé 
     * @return Une ArrayList<EmployeProjet> représentant les données entrées par l'employé pour la semaine entière
     */
    public static ArrayList<EmployeProjet> lireFichier(String nom_fichier){
        Gson gson = new Gson();

        ArrayList<EmployeProjet> employe_projets = new ArrayList<>();
        try {
            Reader lecteur_fichier = Files.newBufferedReader(Paths.get(nom_fichier));
            
            employe_projets = gson.fromJson(lecteur_fichier, new TypeToken<List<EmployeProjet>>() {}.getType());

        } catch (IOException ex) {
            System.out.println("Une erreur est survenue, le fichier d'entrée que vous avez entré n'existe pas");
            System.exit(-1);
        }

        return employe_projets;
    }
}
