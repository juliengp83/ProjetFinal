package com.groupe1.feuilletemps.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST) 
    public String welcomePage(@RequestParam String username, @RequestParam String password){

        if (username.equals("employe") && password.isEmpty())
            return "employe";
        else if (username.equals("gestionnaire") && password.equals("gestionnaire"))
            return "gestionnaire";
        else
            return "login"; // include a login mesage to show unsuccessful login
    }
}