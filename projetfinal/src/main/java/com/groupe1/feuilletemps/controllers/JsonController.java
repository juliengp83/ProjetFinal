package com.groupe1.feuilletemps.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.groupe1.feuilletemps.modeles.EmployeProjet;
import com.groupe1.feuilletemps.modeles.Resultat;
import com.groupe1.feuilletemps.utils.TraitementFeuille;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JsonController {
    @RequestMapping(value = "/submitjson", method = RequestMethod.POST)
    public ResponseEntity<?> posted(@RequestBody String projetsString) {
        Gson gson = new Gson();

        ArrayList<EmployeProjet> employe_projets = new ArrayList<>();

        employe_projets = gson.fromJson(projetsString, new TypeToken<List<EmployeProjet>>() {
        }.getType());
        log.info(employe_projets.toString());

        Resultat resultat = TraitementFeuille.traitement(employe_projets);

        return ResponseEntity.ok(resultat);
    }
}
