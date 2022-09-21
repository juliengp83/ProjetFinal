package com.groupe1.feuilletemps.controllers;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.groupe1.feuilletemps.modeles.EmployeProjet;
import com.groupe1.feuilletemps.modeles.Resultat;
import com.groupe1.feuilletemps.utils.EcritureJson;
import com.groupe1.feuilletemps.utils.LecteurJson;
import com.groupe1.feuilletemps.utils.TraitementFeuille;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JsonController {
    @RequestMapping(value = "/getresultat", method = RequestMethod.POST)
    public ResponseEntity<String> posted(@RequestBody String projetsString) {
        // Transformation du formulaire en objet
        ArrayList<EmployeProjet> employe_projets = LecteurJson.lireStringJson(projetsString);
        log.info(employe_projets.toString());

        // Validation de l'objet représentant le formulaire
        Resultat resultat = TraitementFeuille.traitement(employe_projets);

        // Transformation en Json pour le renvoi à la page
        String jsonResultat = EcritureJson.ecrireJsonString(resultat);
        new Gson().toJson(resultat.getRegles());
        return ResponseEntity.ok(jsonResultat);
    }

    // TODO: coder cette methode
    // Cette methode doit inserer les employeProjets en arguments dans la database
    @RequestMapping(value = "/submitjson", method = RequestMethod.POST)
    public ResponseEntity<Boolean> submit(@RequestBody String projetsString) {
        boolean x = true;
        return ResponseEntity.ok(x);
    }

}
