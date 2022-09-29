package com.groupe1.feuilletemps.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.groupe1.feuilletemps.modeles.EmployeProjet;

public class LecteurJson {

    /** Cette fonction permet la lecture d'une JSON représentant la feuille de temps hebdomadaire complète
     *  d'un employé et la transforme en objet pouvant être validés plus facilement en regard des règles.
     * 
     * @param projetsString String Json représentant le formulaire à transformer en objet
     * @return Une ArrayList<EmployeProjet> représentant les données entrées par l'employé pour la semaine entière
     */
    public static ArrayList<EmployeProjet> lireStringJson(String projetsString){
        Gson gson = new Gson();

        ArrayList<EmployeProjet> employe_projets = new ArrayList<>();
        employe_projets = gson.fromJson(projetsString, new TypeToken<List<EmployeProjet>>() {}.getType());

        return employe_projets;
    }
}
