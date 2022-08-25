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
    
    public EmployeProjet(int id_employe_projet, int numero_employe, int numero_projet, int id_feuille_temps,
            int temps_travaille, Date date_travail) {
        this.id_employe_projet = id_employe_projet;
        this.numero_employe = numero_employe;
        this.numero_projet = numero_projet;
        this.id_feuille_temps = id_feuille_temps;
        this.temps_travaille = temps_travaille;
        this.date_travail = date_travail;
    }

    public int getIdEmployeProjet() {
        return id_employe_projet;
    }

    public void setIdEmployeProjet(int id_employe_projet) {
        this.id_employe_projet = id_employe_projet;
    }

    public int getNumeroEmploye() {
        return numero_employe;
    }

    public void setNumeroEmploye(int numero_employe) {
        this.numero_employe = numero_employe;
    }

    public int getNumeroProjet() {
        return numero_projet;
    }

    public void setNumeroProjet(int numero_projet) {
        this.numero_projet = numero_projet;
    }

    public int getIdFeuilleTemps() {
        return id_feuille_temps;
    }

    public void setIdFeuilleTemps(int id_feuille_temps) {
        this.id_feuille_temps = id_feuille_temps;
    }

    public int getTempsTravail() {
        return temps_travaille;
    }

    public void setTempsTravail(int temps_travaille) {
        this.temps_travaille = temps_travaille;
    }

    public String getDateTravail() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMMM-dd", Locale.CANADA_FRENCH);

        String date = simpleDateFormat.format(date_travail);
        return date;
    }

    public void setDateTravail(Date date_travail) {
        this.date_travail = date_travail;
    }

    @Override
    public String toString() {
        return "EmployeProjet [date_travail=" + getDateTravail() + ", id_employe_projet=" + id_employe_projet
                + ", id_feuille_temps=" + id_feuille_temps + ", numero_employe=" + numero_employe + ", numero_projet="
                + numero_projet + ", temps_travail=" + temps_travaille + "]";
    }
}
