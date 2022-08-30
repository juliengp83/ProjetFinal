package com.groupe1.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groupe1.modeles.Resultat;

public class EcritureJson {

    /** Cette fonction écrit dans un fichier la représentation JSON de l'objet Resultat obtenu par la classe de 
     *  traitement du fichier d'entrée.
     * 
     * @param resultat Le fichier Resultat issue du traitement du fichier d'entrée au regard des règles de l'entreprise
     * @param nom_fichier_resultat Le nom complet du fichier de sortie de résultat
     * @return true si le fichier a été écrit avec succès, false sinon
     */
    public static boolean ecrireFichier(Resultat resultat, String nom_fichier_resultat) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
        BufferedWriter ecriture = Files.newBufferedWriter(Paths.get(nom_fichier_resultat), StandardCharsets.UTF_8, StandardOpenOption.CREATE);

        gson.toJson(resultat, ecriture);

        ecriture.close();

        if(ecriture != null){
            ecriture.close();
            return true;       
        }

        } catch (IOException ex) {
            ex.printStackTrace();
        } 

        return false;
    }
}