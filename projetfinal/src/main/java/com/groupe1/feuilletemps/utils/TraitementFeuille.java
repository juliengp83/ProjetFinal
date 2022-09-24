package com.groupe1.feuilletemps.utils;

import java.util.ArrayList;

import com.groupe1.feuilletemps.modeles.EmployeProjet;
import com.groupe1.feuilletemps.modeles.Regle;
import com.groupe1.feuilletemps.modeles.Resultat;

public class TraitementFeuille {

    private static final int ADMIN_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES = 60 * 36;
    private static final int ADMIN_TEMPS_MAXIMUM_TELETRAVAIL_EN_MINUTES = 60 * 10;
    private static final int ADMIN_TEMPS_REQUIS_QUOTIDIEN_BUREAU = 60 * 4;
    private static final int REGULIER_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES = 60 * 38;
    private static final int REGULIER_TEMPS_REQUIS_QUOTIDIEN_BUREAU = 60 * 6;
    private static final int TEMPS_MAXIMUM_BUREAU_EN_MINUTES = 60 * 43;
    public static boolean projet_speciaux = false;

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
        Resultat resultat = new Resultat();

        int numero_employe = employe_projets.get(0).getNumeroEmploye();

        resultat.setNumeroEmploye(numero_employe);

        // Les règles spécifique a une catégorie d'employée bool est_admin
        if (numero_employe < 1000) // Employee admin
        {
            resultat = employeAdmin(employe_projets, resultat);

        } else { // Employee regulier

            resultat = employeRegulier(employe_projets, resultat);
        }

        resultat = tousLesEmployees(employe_projets, resultat);

        return resultat;
    }

    /**
     * Cette fonction applique les règles pour tous les employées
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @param resultat        l'objet résultat qui est mis à ajour avec les règles
     *                        spécifique des employées administratif.
     * 
     * @return Un objet Resultat qui sera utilisé pour la sortie en format JSON
     */
    public static Resultat tousLesEmployees(ArrayList<EmployeProjet> employe_projets, Resultat resultat) {

        Regle r = new Regle();
        int minutes_travaillees = 0;
        String categorie;

        categorie = "au bureau";
        minutes_travaillees = minutesTravailleesBureau(employe_projets);
        r = tempsMaximumHebdomadaire(minutes_travaillees, TEMPS_MAXIMUM_BUREAU_EN_MINUTES, categorie);
        resultat.ajouterRegle(r);

        int[] temps_quotidien_travaille = calculTempsQuotidienJourSemaine(employe_projets);
        r = tempsMaximumQuotidien(temps_quotidien_travaille, (24 * 60));
        resultat.ajouterRegle(r);

        /*
         * méthode code spéciaux de 995 à 999
         */
        if (projet_speciaux = true) {
            calculMinutesParProjetSpecial(employe_projets, resultat);
        }

        return resultat;
    }

    /**
     * Cette fonction applique les règles spécifiques aux employées administratif
     * (<1000)
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @param resultat        l'objet résultat qui est mis à ajour avec les règles
     *                        spécifique des employées administratif.
     * 
     * @return Un objet Resultat qui sera utilisé pour la sortie en format JSON
     */
    public static Resultat employeAdmin(ArrayList<EmployeProjet> employe_projets, Resultat resultat) {

        Regle r = new Regle();
        int minutes_travaillees = 0;
        String categorie;

        // Regle 1: temps minimum au bureau
        minutes_travaillees = minutesTravailleesBureau(employe_projets);
        r = tempsMinimumHebdomadaire(minutes_travaillees, ADMIN_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES);
        resultat.ajouterRegle(r);

        // Regle 4: temp maximum en télétravail
        minutes_travaillees = minutesTeletravail(employe_projets);
        categorie = "en télétravail";
        r = tempsMaximumHebdomadaire(minutes_travaillees, ADMIN_TEMPS_MAXIMUM_TELETRAVAIL_EN_MINUTES, categorie);
        resultat.ajouterRegle(r);

        // Regle 7: temp minimum quotidien
        int[] temps_quotidien_travaille = calculTempsQuotidienJourSemaine(employe_projets);
        categorie = "au bureau";
        r = tempsMinimumQuotidien(temps_quotidien_travaille, ADMIN_TEMPS_REQUIS_QUOTIDIEN_BUREAU, categorie);
        resultat.ajouterRegle(r);

        return resultat;
    }

    /**
     * Cette fonction applique les règles spécifiques aux employés administratif
     * (>= 1000)
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @param resultat        l'objet résultat qui est mis à ajour avec les règles
     *                        spécifique des employées administratif.
     * @return Resultat Un objet Resultat qui sera utilisé pour la sortie en format
     *         JSON
     */
    public static Resultat employeRegulier(ArrayList<EmployeProjet> employe_projets, Resultat resultat) {
        Regle r = new Regle();
        int minutes_travaillees = 0;
        String categorie;

        // ancienne regle 2: devient temps minimum au bureau.
        minutes_travaillees = minutesTravailleesBureau(employe_projets);
        r = tempsMinimumHebdomadaire(minutes_travaillees, REGULIER_TEMPS_REQUIS_HEBDOMADAIRE_EN_MINUTES);
        resultat.ajouterRegle(r);

        // ancienne regle 6: devient temps minimum quotidient
        categorie = "au bureau";
        int[] temps_quotidien_travaille = calculTempsQuotidienJourSemaine(employe_projets);
        r = tempsMinimumQuotidien(temps_quotidien_travaille, REGULIER_TEMPS_REQUIS_QUOTIDIEN_BUREAU, categorie);
        resultat.ajouterRegle(r);
        
        return resultat;
    }

    /**
     * Cette fonction fait cumul le nombre de minutes de travail au bureau pour un
     * employé incluant le temps de transport 996 et 997
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * 
     * @return Un int, minutes_travaillees_bureau qui sera utilsé pour validation.
     */

    public static int minutesTravailleesBureau(ArrayList<EmployeProjet> employe_projets) {
        int minutes_travaillees_bureau = 0;

        for (EmployeProjet emp_p : employe_projets) {
            if (emp_p.getNumeroProjet() <= 900 || emp_p.getNumeroProjet() == 996 || emp_p.getNumeroProjet() ==997) {
                minutes_travaillees_bureau += emp_p.getTempsTravail();
            }
        }
        return minutes_travaillees_bureau;
    }

    /**
     * Cette fonction fait cumul le nombre de minutes de télétravail (> 900) pour un
     * employé.
     * 
     * Ajout de l'exclusion des numéros de projet inclu entre 995 et 999 ainsi que
     * le boolean qui trigger le processus de validation pour les projet spéciaux.
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
                if (i <= 995 || i >= 999) {
                    minutes_teletravail += emp_p.getTempsTravail();
                } else {
                    projet_speciaux = true;
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
                    + " heures " + categorie + " cette semaine.";
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
     * @param categorie                 Un string qui continet le nom de la
     *                                  catégorie de validation en cour.
     * 
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et
     *         un message détaillé
     */
    public static Regle tempsMinimumQuotidien(int[] temps_quotidien_travaille, int temps_requis, String categorie) {
        Regle regle;
        String message;

        for (int temps_travaille : temps_quotidien_travaille) {
            if (temps_travaille < temps_requis) {
                message = "L'employé n'a pas travaillé les " + temps_requis / 60
                        + " heures requises quotidiennement " + categorie;
                regle = new Regle(7, false, message);
                return regle;
            }
        }

        message = "L'employé a travaillé " + temps_requis / 60
                + " heures " + categorie + " cette semaine.";
        regle = new Regle(7, true, message);

        return regle;
    }

    /**
     * Cette fonction valide la feuille de temps d'un employé par rapport à la
     * règle que les employés de doivent faire un maximum d'heures au bureau pour
     * tout les
     * jours ouvrables.
     * 
     * @param temps_quotidien_travaille Un tableau du cumul quotidien des heures les
     *                                  jours de semaine
     * @param temps_requis              Le nombre de minutes de travail requises
     *                                  quotidiennement.
     * @return Regle Un objet Regle contenant le id_regle, si elle est respectée ou
     *         non et un message détaillé
     */
    public static Regle tempsMaximumQuotidien(int[] temps_quotidien_travaille, int temps_maximum) {
        Regle regle;
        String message;

        for (int temps_travaille : temps_quotidien_travaille) {
            if (temps_travaille >= temps_maximum) {
                message = "L'employé a travaillé plus que les " + temps_maximum / 60
                        + " heures maximal permises quotidiennement.";
                regle = new Regle(8, false, message);
                return regle;
            }
        }

        message = "L'employé n'a pas travaillé plus d'heure que le maximum de " + temps_maximum / 60
                + " heures maximal permises quotidiennement.";
        regle = new Regle(8, true, message);

        return regle;
    }

    /**
     * Cette fonction valide la feuille de temps d'un employé par rapport à la
     * règle que les employés doivent inscrire un nombre d'heures spécifique et exact dans
     * certaines catégories.
     * 
     * @param temps_quotidien_travaille Un tableau du cumul quotidien des heures les
     *                                  jours de semaine
     * @param temps_requis              Le nombre de minutes de travail a valider
     *                                  quotidiennement.
     * @param categorie                 Le nom de la categorie a valider
     * @return Regle Un objet Regle contenant le id_regle, si elle est respectée ou
     *         non et un message détaillé
     */
    public static Regle tempsRequisQuotidien(int[] temps_quotidien_travaille, int temps_requis, String categorie) {
        Regle regle;
        String message;

        for (int temps_travaille : temps_quotidien_travaille) {
            if (temps_travaille == temps_requis) {
                message = "L'employé a inscrit le temps requis de " + temps_requis / 60
                        + " heures pour la catégorie " + categorie;
                regle = new Regle(9, true, message);
                return regle;
            }
        }

        message = "L'employé n'a pas inscrit le requis de " + temps_requis / 60
                + " heures pour la catégorie " + categorie;
        regle = new Regle(9, false, message);

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
        int[] temps_travaille_quotidiennement_semaine = new int[5];
        for (int i = 0; i < 5; i++) {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getJourDeSemaineTravaille() == i + 1) {
                    temps_travaille_quotidiennement_semaine[i] += emp_p.getTempsTravail();
                }
            }
        }
        return temps_travaille_quotidiennement_semaine;
    }

    /**
     * Cette fonction calcule le temps travaillé en minutes pour chaque jour
     * incluant les weke-end.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @return Un tableau de int contenant la temps travaillé en minutes pour chaque
     *         jour l'indice 0 correspond à dimanche et 6 à samedi.
     */
    public static int[] calculTempsQuotidienProjet(ArrayList<EmployeProjet> employe_projets, int projet) {
        int[] temps_travaille_quotidiennement_projet = new int[5];
        for (int i = 0; i < 5; i++) {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getJourDeSemaineTravaille() == i + 1 && emp_p.getNumeroProjet() == projet) {
                    temps_travaille_quotidiennement_projet[i] += emp_p.getTempsTravail();
                }
            }
        }
        return temps_travaille_quotidiennement_projet;
    }

    
    /**
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la
     *                        feuille de temps
     * @param resultat        l'objet résultat qui est mis à ajour avec les règles
     *                        spécifique pour les projets spéciaux.
     */
    // TODO n'est pas terminé. validations supplémentaires a compléter

    public static void calculMinutesParProjetSpecial(ArrayList<EmployeProjet> employe_projets, Resultat resultat) {
        Regle r;
        int[] minutes_par_projet_special = new int[5];
        String categorie = null;
        int temps_requis = 0;

        int i = 0;
        for (EmployeProjet emp_p : employe_projets) {
            i = emp_p.getNumeroProjet();
            
            if (i >= 995 || i <= 999) {// seulement pour réduire les traitements aux seuls projets voulu
                
                // S2 congé parental
                if (i == 995) {
                    categorie = "congé parental";
                    temps_requis = 7 * 60;
                }

                // S2 férié sur semaine = 420 (JOUR_COMPLET) et possible télétravail
                if (i == 998) {
                    categorie = "férié";
                    temps_requis = 7 * 60;
                }

                // S2 Maladie doit être sur semaine et = 420 (JOUR_COMPLET) ne peut pas avoir d'autre projet cette journée
                if (i == 999) {
                    categorie = "maladie";
                    temps_requis = 7 * 60;
                }

                // S3 Transport = 1h max jour ouvrable a inclure au bureau
                if (i == 996 || i == 997) {
                    categorie = "transport";
                    temps_requis = 60;
                }

                minutes_par_projet_special = calculTempsQuotidienProjet(employe_projets, i);
                r = tempsRequisQuotidien(minutes_par_projet_special, temps_requis, categorie);
                resultat.ajouterRegle(r);
            }
        }
    }
}