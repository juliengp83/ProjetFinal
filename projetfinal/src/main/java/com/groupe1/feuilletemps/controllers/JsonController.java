package com.groupe1.feuilletemps.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.groupe1.feuilletemps.modeles.EmployeProjet;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JsonController {
    @RequestMapping(value = "/submitjson", method = RequestMethod.POST)
    public void posted(@RequestBody String projetsString) {
        Gson gson = new Gson();

        ArrayList<EmployeProjet> employe_projets = new ArrayList<>();

        employe_projets = gson.fromJson(projetsString, new TypeToken<List<EmployeProjet>>() {
        }.getType());
        log.info(employe_projets.toString());

    }
}
