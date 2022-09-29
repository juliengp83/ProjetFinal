package com.groupe1.feuilletemps.utils;

import com.google.gson.Gson;
import com.groupe1.feuilletemps.modeles.Resultat;

public class EcritureJson {

    /** Cette fonction écrit dans un fichier la représentation JSON de l'objet Resultat obtenu par la classe de 
     *  traitement du formulaire.
     * 
     * @param resultat Le fichier Resultat issue du traitement du formulaire au regard des règles de l'entreprise
     * @return Une String Json représentant l'objet Resultat 
     */
    public static String ecrireJsonString (Resultat resultat) {
        String jsonResultat = new Gson().toJson(resultat.getRegles());

        return jsonResultat;
    }
}