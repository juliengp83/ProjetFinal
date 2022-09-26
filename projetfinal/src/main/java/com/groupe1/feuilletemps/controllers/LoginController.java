package com.groupe1.feuilletemps.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupe1.feuilletemps.data.EmployeRepository;
import com.groupe1.feuilletemps.modeles.Employe;
import com.groupe1.feuilletemps.modeles.Projet;
import com.groupe1.feuilletemps.utils.AES;


@Controller
public class LoginController {

    private final EmployeRepository employeRepository;

    @Autowired
    public LoginController(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String welcomePage(@RequestParam String username, @RequestParam String password, Model model) {

        if (username.equals("gestionnaire") && password.equals("gestionnaire")) {
            Iterable<Employe> employes = employeRepository.findAll();
            model.addAttribute("employes", employes);

            return "gestionnaire";
        } else { 
            Employe e = employeRepository.findByNomUtilisateur(username);
            if (e != null) {
                if (AES.encrypt(password, "bBgLrINTjBINrm7").equals(e.getMotDePasse())) {
                    Set<Projet> projets = e.getProjets();
                    model.addAttribute("projets", projets);
                    model.addAttribute("e", e);
                    return "employe";
                } else {
                    model.addAttribute("msg", "Mauvais mot de passe");
                    return "login";
                }
            } else {
                model.addAttribute("msg", "Employé non trouvé");
                return "login";
            }
        }
    }
}