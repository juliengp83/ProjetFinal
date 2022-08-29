package com.groupe1.modeles;

import java.util.ArrayList;

/* Test #1 : Les employes de l'administration (numero_employe est INFERIEUR à 1000) doivent travailler AU MOINS 
   36 heures au bureau par semaine (code_projet INFERIEUR à 900).
   Test #2 : Les employes normaux (numero_employe est SUPERIEUR à 1000) doivent travailler AU MOINS 38 heures au
   bureau par semaine (code_projet inferieur à 900).
   Test #3 : Aucun employé n'a le droit de passer plus de 43 heures au bureau (code_projet inferieur à 900).
   Test #4 : Les employés de l'administration (numero_employe est INFERIEUR a 1000) ne peuvent pas faire plus de
   10 heures de télétravail (code_projet SUPÉRIEUR à 1000) par semaine.
   Test #5 : Les employés normaux (numero_employe est SUPERIEUR à 1000) peuvent faire autant de télétravail 
   (code_projet supérieur à 1000) qu'ils le souhaitent.
   Test #6 : Les employés normaux (numero_employe superieur à 1000) doivent faire un minimum de 6 heures au 
   bureau (code_projet inferieur à 1000) les jours ouvrables.
   Test #7 : Les employés de l'administration (numero_employe inferieur à 1000) doivent faire un minimum de 4 heures
   au bureau (code_projet inferieur à 1000) les jours ouvrables.
 */

public class Resultat {    
    private int numero_employe;
    private ArrayList<Regle> regles = new ArrayList<Regle>();
    
    public Resultat() {
        
    }

    public Resultat(int numero_employe) {
        this.numero_employe = numero_employe;
        regles = new ArrayList<>();
    }
    
    public Resultat(int numero_employe, ArrayList<Regle> regles) {
        this.numero_employe = numero_employe;
        this.regles = regles;
    }

    public int getNumeroEmploye() {
        return numero_employe;
    }

    public void setNumeroEmploye(int numero_employe) {
        this.numero_employe = numero_employe;
    }

    public ArrayList<Regle> getRegles() {
        return regles;
    }

    public void setRegles(ArrayList<Regle> regles) {
        this.regles = regles;
    }

    public void ajouterRegle(Regle regle) {
        this.regles.add(regle);
    }

    @Override
    public String toString() {
        return "Resultat [numero_employe=" + numero_employe + ", regles=" + regles + "]";
    }
}