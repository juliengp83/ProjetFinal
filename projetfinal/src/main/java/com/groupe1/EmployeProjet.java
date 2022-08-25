package com.groupe1;

import java.util.Date;

public class EmployeProjet {
    public int id_employeprojet;
    public int numero_employe;
    public int numero_projet;
    public int id_feuille_temps;
    public int temps_travail;
    public Date date_travail;

    public int getId_employeprojet() {
        return id_employeprojet;
    }

    public void setId_employeprojet(int id_employeprojet) {
        this.id_employeprojet = id_employeprojet;
    }

    public int getNumero_employe() {
        return numero_employe;
    }

    public void setNumero_employe(int numero_employe) {
        this.numero_employe = numero_employe;
    }

    public int getNumero_projet() {
        return numero_projet;
    }

    public void setNumero_projet(int numero_projet) {
        this.numero_projet = numero_projet;
    }

    public int getId_feuille_temps() {
        return id_feuille_temps;
    }

    public void setId_feuille_temps(int id_feuille_temps) {
        this.id_feuille_temps = id_feuille_temps;
    }

    public int getTemps_travail() {
        return temps_travail;
    }

    public void setTemps_travail(int temps_travail) {
        this.temps_travail = temps_travail;
    }

    public Date getDate_travail() {
        return date_travail;
    }

    public void setDate_travail(Date date_travail) {
        this.date_travail = date_travail;
    }

    public EmployeProjet(int id_employeprojet, int numero_employe, int numero_projet, int id_feuille_temps,
            int temps_travail, Date date_travail) {
        this.id_employeprojet = id_employeprojet;
        this.numero_employe = numero_employe;
        this.numero_projet = numero_projet;
        this.id_feuille_temps = id_feuille_temps;
        this.temps_travail = temps_travail;
        this.date_travail = date_travail;
    }

    @Override
    public String toString() {
        return "EmployeProjet [date_travail=" + date_travail + ", id_employeprojet=" + id_employeprojet
                + ", id_feuille_temps=" + id_feuille_temps + ", numero_employe=" + numero_employe + ", numero_projet="
                + numero_projet + ", temps_travail=" + temps_travail + "]";
    }
}
