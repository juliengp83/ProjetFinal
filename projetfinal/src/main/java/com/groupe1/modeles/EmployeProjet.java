package com.groupe1.modeles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmployeProjet {
    private int id_employe_projet;
    private int numero_employe;
    private int numero_projet;
    private int id_feuille_temps;
    private int temps_travaille;
    private Date date_travail;

    public EmployeProjet() {
    }
    
    public EmployeProjet(int id_employeprojet, int numero_employe, int numero_projet, int id_feuille_temps,
            int temps_travail, Date date_travail) {
        this.id_employe_projet = id_employeprojet;
        this.numero_employe = numero_employe;
        this.numero_projet = numero_projet;
        this.id_feuille_temps = id_feuille_temps;
        this.temps_travaille = temps_travail;
        this.date_travail = date_travail;
    }

    public int getId_employeprojet() {
        return id_employe_projet;
    }

    public void setId_employeprojet(int id_employeprojet) {
        this.id_employe_projet = id_employeprojet;
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
        return temps_travaille;
    }

    public void setTemps_travail(int temps_travail) {
        this.temps_travaille = temps_travail;
    }

    public String getDate_travail() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMMM-dd", Locale.CANADA_FRENCH);

        String date = simpleDateFormat.format(date_travail);
        return date;
    }

    public void setDate_travail(Date date_travail) {
        this.date_travail = date_travail;
    }

    @Override
    public String toString() {
        return "EmployeProjet [date_travail=" + getDate_travail() + ", id_employeprojet=" + id_employe_projet
                + ", id_feuille_temps=" + id_feuille_temps + ", numero_employe=" + numero_employe + ", numero_projet="
                + numero_projet + ", temps_travail=" + temps_travaille + "]";
    }
}
