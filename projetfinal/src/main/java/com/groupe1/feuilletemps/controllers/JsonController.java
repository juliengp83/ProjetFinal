package com.groupe1.feuilletemps.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.groupe1.feuilletemps.data.EmployeRepository;
import com.groupe1.feuilletemps.data.EntreeProjetRepository;
import com.groupe1.feuilletemps.data.FeuilleDeTempsRepository;
import com.groupe1.feuilletemps.data.ProjetRepository;
import com.groupe1.feuilletemps.modeles.Employe;
import com.groupe1.feuilletemps.modeles.EmployeProjet;
import com.groupe1.feuilletemps.modeles.EntreeProjet;
import com.groupe1.feuilletemps.modeles.FeuilleDeTemps;
import com.groupe1.feuilletemps.modeles.Projet;
import com.groupe1.feuilletemps.modeles.Regle;
import com.groupe1.feuilletemps.modeles.Resultat;
import com.groupe1.feuilletemps.utils.EcritureJson;
import com.groupe1.feuilletemps.utils.LecteurJson;
import com.groupe1.feuilletemps.utils.TraitementFeuille;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JsonController {
    private final EmployeRepository repo_emp;
    private final ProjetRepository repo_projet;
    private final FeuilleDeTempsRepository repo_feuille;
    private final EntreeProjetRepository repo_entree_projet;
    private static boolean est_validee = true;

    public JsonController(EmployeRepository repo_emp, ProjetRepository repo_projet,
            FeuilleDeTempsRepository repo_feuille, EntreeProjetRepository repo_entree_projet) {
        this.repo_emp = repo_emp;
        this.repo_projet = repo_projet;
        this.repo_feuille = repo_feuille;
        this.repo_entree_projet = repo_entree_projet;
    }

    @RequestMapping(value = "/getresultat", method = RequestMethod.POST)
    public ResponseEntity<String> posted(@RequestBody String projetsString) {
        // On assume que le formulaire respecte les regles
        est_validee = true;
        // Transformation du formulaire en objet
        ArrayList<EmployeProjet> employe_projets = LecteurJson.lireStringJson(projetsString);

        // Validation de l'objet représentant le formulaire
        Resultat resultat = TraitementFeuille.traitement(employe_projets);

        ArrayList<Regle> regles = resultat.getRegles();

        for (Regle r : regles) {
            if (!r.isRespectee()) {
                est_validee = false;
                break;
            }
        }
        // Transformation du Resultat en Json pour le renvoi à la page
        String jsonResultat = EcritureJson.ecrireJsonString(resultat);
        return ResponseEntity.ok(jsonResultat);
    }

    @RequestMapping(value = "/submitjson", method = RequestMethod.POST)
    public ResponseEntity<Boolean> submit(@RequestBody String projetsString) {

        if (est_validee) {
            ajouterFeuilleTemps(projetsString, est_validee);

        } else {
            ajouterFeuilleTemps(projetsString, est_validee);
        }

        return ResponseEntity.ok(true);
    }

    public void ajouterFeuilleTemps(String projetsString, boolean estValidee) {
        ArrayList<EmployeProjet> employe_projets = LecteurJson.lireStringJson(projetsString);
        ArrayList<EntreeProjet> entree_projets = new ArrayList<>();
        populerEntreeProjets(employe_projets, entree_projets);
        Long numeroEmploye = Long.valueOf(employe_projets.get(0).getNumeroEmploye());
        Employe e = repo_emp.findById(numeroEmploye).orElse(null);
        Date dateSemaine = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7));

        FeuilleDeTemps feuille_temps = new FeuilleDeTemps(e, dateSemaine, estValidee, false, entree_projets);
        repo_feuille.save(feuille_temps);
    }

    public void populerEntreeProjets(ArrayList<EmployeProjet> employe_projets, ArrayList<EntreeProjet> entree_projets) {
        for (EmployeProjet ep : employe_projets) {
            Long numeroProjet = Long.valueOf(ep.getNumeroProjet());
            int tempsTravaille = ep.getTempsTravail();
            Date dateTravaille = ep.getDateTravail();
            Projet p = repo_projet.findById(numeroProjet).orElse(null);
            EntreeProjet entree = new EntreeProjet(p, tempsTravaille, dateTravaille);
            repo_entree_projet.save(entree);
            entree_projets.add(entree);
        }
    }
}