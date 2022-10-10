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

@Controller
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

    /** Ce mapping de requête reçoit le formulaire rempli de l'employé du body de la requête AJAX et renvoi 
     *  l'objet Resultat sérialisé à la page. Si ce formulaire ne respecte pas toute les règles on met à jour
     *  une variable globale qui servira si on décide d'envoyer tout de même le formulaire malgré les erreurs.
     * 
     * @param projetsString La liste d'objets EmployeProjet correspondant au formulaire de l'employé pour une semaine
     * @return L'objet Resultat indiquant si le formulaire respecte les règles de l'entreprise
     */
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
    
    /** Mapping de requête utilisé pour l'envoi du formulaire vers le serveur. On vérifie la variable globale afin
     *  de savoir s'il s'agit d'un formulaire validé et on le passe à la fonction ajouterFeuilleTemps(String, boolean).
     * 
     * @param projetsString La liste d'objets EmployeProjet correspondant au formulaire de l'employé pour une semaine
     * @return Un booleen true indiquant que la feuille de temps a bien été persistée
     */
    @RequestMapping(value = "/submitjson", method = RequestMethod.POST)
    public ResponseEntity<Boolean> submit(@RequestBody String projetsString) {

        if (est_validee) {
            ajouterFeuilleTemps(projetsString, est_validee);

        } else {
            ajouterFeuilleTemps(projetsString, est_validee);
        }

        return ResponseEntity.ok(true);
    }

    /** Cette méthode s'occupe de persister la feuille de temps dans la base de données
     * 
     * @param projetsString La liste d'objets EmployeProjet correspondant au formulaire de l'employé pour une semaine
     * @param estValidee détermine si la feuille a été validée ou non par rapport au respect des règles
     */
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

    /** Cette méthode s'occupe de populer le tableau d'entrée projet relatif à une feuille de temps et les persiste 
     *  dans la base de données, le ArrayList<EntreeProjet> entree_projets contiendra donc toute les EntreeProjet
     *  contenues dans le formulaire de l'employé
     * 
     * @param employe_projets Un ArrayList<EmployeProjet> correspondant au formulaire de l'employé
     * @param entree_projets Un ArrayList<EntreeProjet> vide qui sera remplit et persisté avec la feuille de temps
     */
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