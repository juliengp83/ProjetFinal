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
    public static ArrayList<EmployeProjet> lireFichier(String nom_fichier){
        Gson gson = new Gson();

        ArrayList<EmployeProjet> employe_projets = new ArrayList<>();
        try {
            Reader lecteur_fichier = Files.newBufferedReader(Paths.get(nom_fichier));
            
            employe_projets = gson.fromJson(lecteur_fichier, new TypeToken<List<EmployeProjet>>() {}.getType());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return employe_projets;
    }
}
