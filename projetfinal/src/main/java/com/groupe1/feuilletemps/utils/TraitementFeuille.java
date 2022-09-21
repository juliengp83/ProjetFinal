package com.groupe1.feuilletemps.utils;

import java.util.ArrayList;

import com.groupe1.feuilletemps.modeles.EmployeProjet;
import com.groupe1.feuilletemps.modeles.Regle;
import com.groupe1.feuilletemps.modeles.Resultat;

public class TraitementFeuille {

    static int ADMIN_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES = 60 * 36;
    static int ADMIN_TEMPS_MAXIMUN_TELETRAVAIL_EN_MINUTES = 60 * 10;
    static int ADMIN_TEMPS_REQUIS_QUOTIDIEN_BUREAU = 60 * 4;
    static int REGULIER_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES = 60 * 38;
    static int REGULIER_TEMPS_REQUIS_QUOTIDIEN_BUREAU = 60 * 6;
    static int TEMPS_MAXIMUM_BUREAU_EN_MINUTES = 60 * 43;
    static int JOUR_COMPLET = 60 * 7;

    /**
     * Cette fonction retourne un objet de type Resultat qui contient un numéro
     * d'employé ainsi qu'un ArrayList<Regle>
     * représentant l'ensemble des règles de l'entreprise. Ces objets Regle
     * contiendront le id_regle associé, si elle
     * est respectée ou non et un message détaillé.
     * 
     * Cet objet Resultat servira ensuite à l'écriture du fichier de sortie.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @return Un objet Resultat qui sera utilisé pour la sortie en format JSON
     */
    public static Resultat traitement(ArrayList<EmployeProjet> employe_projets) {
        Resultat res = new Resultat();
        Regle r = new Regle();

        int numero_employe = employe_projets.get(0).getNumeroEmploye();
        int minutes_travaillees = 0;
        String categorie;

        res.setNumeroEmploye(numero_employe);

        // Les règles spécifique a une catégorie d'employée
        if (numero_employe < 1000) // Employee admin
        {
            // Regle 1: temps minimum au bureau
            minutes_travaillees = minutesTravailleesBureau(employe_projets);
            r = tempsMinimumHebdomadaire(minutes_travaillees, ADMIN_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES);
            res.ajouterRegle(r);

            // Regle 4: temp maximum en télétravail
            minutes_travaillees = minutesTeletravail(employe_projets);
            categorie = "en télétravail";
            r = tempsMaximumHebdomadaire(minutes_travaillees, ADMIN_TEMPS_MAXIMUN_TELETRAVAIL_EN_MINUTES, categorie);
            res.ajouterRegle(r);

            // Regle 7: temp minimum quotidien
            int[] temps_quotidien_travaille = calculTempsQuotidienJourSemaine(employe_projets);
            r = tempsMinimumQuotidien(temps_quotidien_travaille, ADMIN_TEMPS_REQUIS_QUOTIDIEN_BUREAU);
            res.ajouterRegle(r);

        } else { // Employee regulier

            // ancienne regle 2: devient temps minimum au bureau.
            minutes_travaillees = minutesTravailleesBureau(employe_projets);
            r = tempsMinimumHebdomadaire(minutes_travaillees, REGULIER_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES);
            res.ajouterRegle(r);

            // ancienne regle 6: devient temps minimum quotidient
            int[] temps_quotidien_travaille = calculTempsQuotidienJourSemaine(employe_projets);
            r = tempsMinimumQuotidien(temps_quotidien_travaille, REGULIER_TEMPS_REQUIS_QUOTIDIEN_BUREAU);
            res.ajouterRegle(r);
        }
        // Règles pour tous les employées
        minutes_travaillees = minutesTravailleesBureau(employe_projets);
        categorie = "au bureau";
        r = tempsMaximumHebdomadaire(minutes_travaillees, TEMPS_MAXIMUM_BUREAU_EN_MINUTES, categorie);
        res.ajouterRegle(r);

        return res;
    }

    /**
     * Cette fonction fait cumul le nombre de minutes de travail au bureau pour un
     * employé
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @return Un int, minutes_travaillees_bureau qui sera utilsé pour validation.
     */
    public static int minutesTravailleesBureau(ArrayList<EmployeProjet> employe_projets) {
        int minutes_travaillees_bureau = 0;

        for (EmployeProjet emp_p : employe_projets) {
            if (emp_p.getNumeroProjet() <= 900) {
                minutes_travaillees_bureau += emp_p.getTempsTravail();
            }
        }
        return minutes_travaillees_bureau;
    }

    /**
     * Cette fonction fait cumul le nombre de minutes de télétravail (> 900) pour un
     * employé
     * Ajout de l'exclusion des numéros de projet inclu entre 995 et 999
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @return minutes_teletravail en int
     */
    public static int minutesTeletravail(ArrayList<EmployeProjet> employe_projets) {
        int minutes_teletravail = 0;
        int i = 0;
        for (EmployeProjet emp_p : employe_projets) {
            i = emp_p.getNumeroProjet();
            if (i > 900) {
                if (i <= 995 && i >= 999) {
                    minutes_teletravail += emp_p.getTempsTravail();
                }
            }
        }
        return minutes_teletravail;
    }

    /**
     * Cette fonction valide si la feuille de temps de l'employé respecte le minimum
     * hebdomadaire d'heures requises pour un employé.
     * 
     * @param minutes_travaillees Le cumul du temps travaillées pour la semaine.
     * @param temps_requis        Le nombre de minutes de travail requises.
     * 
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et
     *         un message détaillé
     */
    public static Regle tempsMinimumHebdomadaire(int minutes_travaillees, int temps_requis) {
        Regle regle;
        boolean est_respectee = true;
        String message;

        if (minutes_travaillees < temps_requis) {
            message = "L'employé n'a pas travaillé au moins les " + temps_requis / 60
                    + " heures requises cette semaine.";
            est_respectee = false;
        } else {
            message = "L'employé a travaillé au moins les " + temps_requis / 60
                    + "  heures requises cette semaine.";
            est_respectee = true;
        }
        regle = new Regle(1, est_respectee, message);
        return regle;
    }

    /**
     * Cette fonction valide si l'employé respecte le plafond hebdomadaire maximum
     * d'heure
     * pour la catégorie.
     * 
     * @param minutes_travaillees Le cumul du temps travaillées pour la semaine.
     * @param temps_maximum       Le maximum permis pour la catégorie.
     * @param categorie           Un string qui contient le nom de la catégorie.
     * 
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et
     *         un message détaillé
     */
    public static Regle tempsMaximumHebdomadaire(int minutes_travaillees, int temps_maximum, String categorie) {
        Regle regle;
        boolean est_respectee = true;
        String message = "L'employé a travaillé moins de " + temps_maximum / 60
                + " heures " + categorie + " cette semaine.";

        if (minutes_travaillees > temps_maximum) {
            message = "L'employé a travaillé plus de " + temps_maximum / 60
                    + "  heures " + categorie + " cette semaine.";
            est_respectee = false;
        }

        regle = new Regle(4, est_respectee, message);
        return regle;
    }

    /**
     * Cette fonction valide la feuille de temps d'un employé par rapport à la règle
     * que les employés de doivent faire un minimum d'heures au bureau pour tout les
     * jours ouvrables.
     * 
     * @param temps_quotidien_travaille Un tableau du cumul quotidien des heures les
     *                                  jours de semaine
     * @param temps_requis              Le nombre de minutes de travail requises
     *                                  quotidiennement.
     * 
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et
     *         un message détaillé
     */
    public static Regle tempsMinimumQuotidien(int[] temps_quotidien_travaille, int temps_requis) {
        Regle regle;
        String message;

        for (int temps_travaille : temps_quotidien_travaille) {
            if (temps_travaille < temps_requis) {
                message = "L'employé a travaillé moins de " + temps_requis / 60
                        + " heures au bureau cette semaine durant un des jours ouvrables.";
                regle = new Regle(7, false, message);
                return regle;
            }
        }
        message = "L'employé a travaillé " + temps_requis / 60
                + " heures ou plus au bureau cette semaine durant tous les jours ouvrables.";
        regle = new Regle(7, true, message);

        return regle;
    }

    /**
     * Cette fonction calcule le temps travaillé en minutes pour chaque chaque jour
     * ouvrable.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @return Un tableau de int contenant la temps travaillé en minutes pour chaque
     *         jour ouvrable (lundi au vendredi)
     *         l'indice 0 correspond à lundi et 4 à vendredi.
     */
    public static int[] calculTempsQuotidienJourSemaine(ArrayList<EmployeProjet> employe_projets) {
        int[] temps_travaille_quotidiennement_bureau = new int[5];
        for (int i = 0; i < 5; i++) {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getJourDeSemaineTravaille() == i + 1) {
                    temps_travaille_quotidiennement_bureau[i] += emp_p.getTempsTravail();
                }
            }
        }
        return temps_travaille_quotidiennement_bureau;
    }

    // TODO n'est pas terminé
    public static int[] minutesParProjetSpecial(ArrayList<EmployeProjet> employe_projets) {
        int[] minutes_par_projet_special = new int[5];
        // {995,996,997,998,999};
        int i = 0;
        for (EmployeProjet emp_p : employe_projets) {
            i = emp_p.getNumeroProjet();
            if (i >= 995 && i <= 999) {
                switch (i) {
                    case 995:
                        minutes_par_projet_special[0] += emp_p.getTempsTravail();
                    case 996:
                        minutes_par_projet_special[1] += emp_p.getTempsTravail();
                    case 997:
                        minutes_par_projet_special[2] += emp_p.getTempsTravail();
                    case 998:
                        minutes_par_projet_special[3] += emp_p.getTempsTravail();
                    case 999:
                        minutes_par_projet_special[4] += emp_p.getTempsTravail();
                }
            }
        }
        return minutes_par_projet_special;
    }

}