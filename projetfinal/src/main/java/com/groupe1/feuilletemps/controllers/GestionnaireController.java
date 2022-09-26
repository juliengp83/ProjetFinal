package com.groupe1.feuilletemps.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.groupe1.feuilletemps.data.FeuilleDeTempsRepository;
import com.groupe1.feuilletemps.modeles.FeuilleDeTemps;

@Controller
public class GestionnaireController {
    private final FeuilleDeTempsRepository repo_feuilles;

    @Autowired
    public GestionnaireController(FeuilleDeTempsRepository repo_feuilles) {
        this.repo_feuilles = repo_feuilles;
    }


    @RequestMapping(value = "/getfeuilles", method = RequestMethod.GET)
    public ResponseEntity<String> envoyerFeuilles(@RequestParam String selectedWeek) {

     
        Iterable<FeuilleDeTemps> feuilles = repo_feuilles.getFeuillesDeTempsByWeekOfYear(Integer.parseInt(selectedWeek));
        List<FeuilleDeTemps> feuillesDemandees = new ArrayList<>();
        feuilles.forEach(feuillesDemandees::add);

        String feuilleJson = new Gson().toJson(feuillesDemandees);

        return ResponseEntity.ok(feuilleJson.toString());
    }

    @RequestMapping(value = "/approuvefeuille", method = RequestMethod.GET)
    public ResponseEntity<Boolean> approuverFeuille(@RequestParam String feuilleId) {

        FeuilleDeTemps f= repo_feuilles.findById(Long.valueOf(feuilleId)).orElse(null);
        f.setEstApprouvee(true);
        repo_feuilles.save(f);

        return ResponseEntity.ok(true);
    }
 

    
}
