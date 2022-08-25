package com.groupe1.utils;

import java.util.ArrayList;

import com.groupe1.modeles.EmployeProjet;
import com.groupe1.modeles.Regle;
import com.groupe1.modeles.Resultat;

public class TraitementFeuille {

    
    public static Resultat traitement(ArrayList<EmployeProjet> employe_projets) {
        Resultat res = new Resultat();

        Regle r = testRegle1();
        res.ajouterRegle(r);
        r = testRegle2();
        res.ajouterRegle(r);
        r = testRegle3();
        res.ajouterRegle(r);
        r = testRegle4();
        res.ajouterRegle(r);
        r = testRegle5();
        res.ajouterRegle(r);       
        r = testRegle6();
        res.ajouterRegle(r);       
        r = testRegle7();
        res.ajouterRegle(r);       
        
        return res;
    }
    
    public static Regle testRegle1() {
        Regle regle;
        
        // faire les test
        
        regle = new Regle(1, true, "le message");
        return regle;
    }

    public static Regle testRegle2() {
        Regle regle = new Regle();

        return regle;
    }

    public static Regle testRegle3() {
        Regle regle = new Regle();
        
        return regle;
    }

     public static Regle testRegle4() {
        Regle regle = new Regle();

        return regle;
    }   

    public static Regle testRegle5() {
        Regle regle = new Regle();

        return regle;
    }

    public static Regle testRegle6() {
        Regle regle = new Regle();

        return regle;
    }

    public static Regle testRegle7() {
        Regle regle = new Regle();

        return regle; 
    }
}
