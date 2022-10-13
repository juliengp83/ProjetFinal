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


    /** Ce mapping de requête est en charge de renvoyer les feuilles de temps sérialisées en JSON de la semaine 
     *  correspondant au numéro de semaine de l'année passé en paramètre
     * 
     * @param selectedWeek Paramètre reçu dans l'URL de l'appel AJAX correspondant au numéro de la semaine pour
     * laquelle on veut obtenir les feuilles de temps
     * @return Une string JSON des feuilles de temps sérialisées pour la semaine
     */
    @RequestMapping(value = "/getfeuilles", method = RequestMethod.GET)
    public ResponseEntity<String> envoyerFeuilles(@RequestParam String selectedWeek) {

     
        Iterable<FeuilleDeTemps> feuilles = repo_feuilles.getFeuillesDeTempsByWeekOfYear(Integer.parseInt(selectedWeek));
        List<FeuilleDeTemps> feuillesDemandees = new ArrayList<>();
        feuilles.forEach(feuillesDemandees::add);

        String feuilleJson = new Gson().toJson(feuillesDemandees);

        return ResponseEntity.ok(feuilleJson.toString());
    }

    /** Ce mapping de requête est en charge d'approuver la feuille de temps dont le id est passé en paramètre et de
     *  renvoyer la liste des feuilles de temps à la page
     * 
     * @param id Paramètre reçu par l'appel AJAX correspondant au id de la feuille de temps à approuver
     * @return Une String JSON contenant toute les feuilles de temps
     */
    @RequestMapping(value = "/approuvefeuille", method = RequestMethod.POST)
    public ResponseEntity<String> approuverFeuille(@RequestParam String id) {

        Long feuilleId = Long.valueOf(id);
        FeuilleDeTemps f = repo_feuilles.findById(feuilleId).orElse(null);
        f.setEstApprouvee(true);
        repo_feuilles.save(f);

        Iterable<FeuilleDeTemps> feuilles = repo_feuilles.findAll();
        List<FeuilleDeTemps> feuillesDemandees = new ArrayList<>();
        feuilles.forEach(feuillesDemandees::add);
        String feuilleJson = new Gson().toJson(feuillesDemandees);

        return ResponseEntity.ok(feuilleJson);
    }
}