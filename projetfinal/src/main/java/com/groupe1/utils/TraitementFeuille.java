package com.groupe1.utils;

import java.util.ArrayList;

import com.groupe1.modeles.EmployeProjet;
import com.groupe1.modeles.Regle;
import com.groupe1.modeles.Resultat;

public class TraitementFeuille {

    /** Cette fonction retourne un objet de type Resultat qui contient un numéro d'employé ainsi qu'un ArrayList<Regle>
     *  représentant l'ensemble des règles de l'entreprise. Ces objets Regle contiendront le id_regle associé, si elle
     *  est respectée ou non et un message détaillé.
     * 
     *  Cet objet Resultat servira ensuite à l'écriture du fichier de sortie.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Resultat qui sera utilisé pour la sortie en format JSON
     */
    public static Resultat traitement(ArrayList<EmployeProjet> employe_projets) {
        Resultat res = new Resultat();
        Regle r = new Regle();

        res.setNumeroEmploye(employe_projets.get(0).getNumeroEmploye());

        r = testRegle1(employe_projets);
        res.ajouterRegle(r);

        r = testRegle2(employe_projets);
        res.ajouterRegle(r);

        r = testRegle3(employe_projets);
        res.ajouterRegle(r);
        
        r = testRegle4(employe_projets);
        res.ajouterRegle(r);

        r = testRegle5(employe_projets);
        res.ajouterRegle(r);
      
        r = testRegle6(employe_projets);
        res.ajouterRegle(r); 

        r = testRegle7(employe_projets);
        res.ajouterRegle(r);       

        return res;
    }
    
    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #1 qui stipule que les employés
     *  de l'administration doivent travailler au moins 36 heures au bureau par semaine.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé
     */
    public static Regle testRegle1(ArrayList<EmployeProjet> employe_projets) {
        Regle regle;
        boolean respectee = true;
        String message = "L'employé administratif a travaillé au moins 36 heures au bureau cette semaine.";
        int minutes_travaillees_bureau = 0;
        int temps_requis_en_minutes = 60 * 36;
        int numero_employe = employe_projets.get(0).getNumeroEmploye();

        if (numero_employe > 1000) {
            message = "Il s'agit d'un employé régulier qui n'est pas assujetti à cette règle.";
        } else {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getNumeroProjet() <= 900) {
                    minutes_travaillees_bureau += emp_p.getTempsTravail();
                }
            }

            if (minutes_travaillees_bureau < temps_requis_en_minutes) {
                message = "L'employé administratif n'a pas travaillé au moins 36 heures au bureau cette semaine.";
                respectee = false;
            }
        }

        regle = new Regle(1, respectee, message);
        return regle;
    }

    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #2 qui stipule que les employés
     *  réguliers doivent travailler au moins 38 heures au bureau par semaine.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé
     */
    public static Regle testRegle2(ArrayList<EmployeProjet> employe_projets) {
        Regle regle;
        String message;
        int minutes_travaillees_bureau = 0;
        int temps_requis_en_minutes = 60*38;
        int numero_employe = employe_projets.get(0).getNumeroEmploye();
        if (numero_employe < 1000) {
            message = "Il s'agit d'un employé de l'administration donc il remplit forcément cette règle";
            regle = new Regle(2, true, message);
        }
        else {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getNumeroProjet() <= 900) 
                    minutes_travaillees_bureau += emp_p.getTempsTravail();
            }

            if (minutes_travaillees_bureau < temps_requis_en_minutes) {
                message = "L'employé régulier n'a pas travaillé au moins 38 heures au bureau cette semaine.";
                regle = new Regle(2, false, message);
            } else {
                message = "L'employé régulier a travaillé au moins 38 heures au bureau cette semaine.";
                regle = new Regle(2, true, message);
            }
        }

        return regle;
    }

    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #3 qui stipule qu'aucun employé
     *  n'a le droit de passer plus de 43 heures au bureau par semaine.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé 
     */
    public static Regle testRegle3(ArrayList<EmployeProjet> employe_projets ) {
        Regle regle;
        String message;
        int minutes_travaillees_bureau = 0;
        int temps_requis_en_minutes = 60*43;

                for (EmployeProjet emp_p : employe_projets) {
                    if (emp_p.getNumeroProjet() <= 900)
                    minutes_travaillees_bureau += emp_p.getTempsTravail(); 
                }

                if (minutes_travaillees_bureau > temps_requis_en_minutes) {
                    message = "L'employé a travaillé plus de 43 heures au bureau cette semaine.";
                    regle = new Regle(3, false, message);
                } else {
                    message = "L'employé n'a pas travaillé plus de 43 heures au bureau cette semaine."; 
                    regle = new Regle(3, true, message);
                }
        
        return regle;
    }

    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #4 qui stipule que les employés
     *  de l'administration ne peuvent faire plus que 10 heures de télétravail par semaine.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé
     */
    public static Regle testRegle4(ArrayList<EmployeProjet> employe_projets) {
        Regle regle;
        boolean respectee = true;
        String message = "L'employé administratif a travaillé moins 10 heures en télétravail cette semaine.";
        int minutes_travaillees_bureau = 0;
        int temps_maximum_en_minutes = 60 * 10;
        int numero_employe = employe_projets.get(0).getNumeroEmploye();

        if (numero_employe > 1000) {
            message = "Il s'agit d'un employé régulier qui n'est pas assujetti à cette règle.";
        } else {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getNumeroProjet() > 900) {
                    minutes_travaillees_bureau += emp_p.getTempsTravail();
                }
            }

            if (minutes_travaillees_bureau <= temps_maximum_en_minutes) {
                message = "L'employé administratif a travaillé plus de 10 heures en télétravail cette semaine.";
                respectee = false;
            }
        }

        regle = new Regle(4, respectee, message);
        return regle;
    }   

    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #5 qui stipule que les employés
     *  réguliers peuvent faire autant de télétravail qu'ils le souhaitent.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé
     */
    public static Regle testRegle5(ArrayList<EmployeProjet> employe_projets) {
        String message = "L'employé régulier peut faire autant de télétravail qu'il le veut.";
        int numero_employe = employe_projets.get(0).getNumeroEmploye();

        if (numero_employe < 1000) {
            message = "Il s'agit d'un employé administratif qui n'est pas assujetti à cette règle.";
        }

        Regle regle = new Regle(5, true, message);

        return regle;
    }

    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #6 qui stipule que les employés
     *  réguliers doivent faire un minimum de 6 heures au bureau tout les jours ouvrables.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé
     */
    public static Regle testRegle6(ArrayList<EmployeProjet> employe_projets) {
        Regle regle;
        String message;
        int minutes_travaillees_bureau = 0;
        int temps_requis_en_minutes = 60*6;
        int numero_employe = employe_projets.get(0).getNumeroEmploye();

        if (numero_employe < 1000) {
            message = "Il s'agit d'un employé de l'administration qui n'est pas assujetti à cette règle.";
            regle = new Regle(6, true, message);
        } else {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getNumeroProjet() <= 900 && emp_p.getJourDeSemaineTravaille() >= 1 
                    && emp_p.getJourDeSemaineTravaille() <= 5) {
                    minutes_travaillees_bureau += emp_p.getTempsTravail();
                }
            }
            if (minutes_travaillees_bureau < temps_requis_en_minutes) {
                message = "L'employé a travaillé moins de 6 heures au bureau cette semaine durant les jours ouvrables.";
                regle = new Regle(6, false, message);
            } else {
               message = "L'employé a travaillé 6 heures ou plus au bureau cette semaine durant les jours ouvrables."; 
               regle = new Regle(6, true, message);
            }
        }

        return regle;
    }

    /** Cette fonction valide la feuille de temps d'un employé par rapport à la règle #7 qui stipule que les employés de
     *  l'administration doivent faire un minimum de 4 heures au bureau pour tout les jours ouvrables.
     * 
     * @param employe_projets Le tableau d'EmployeProjet extrait de la lecture de la feuille de temps
     * @return Un objet Regle contenant le id_regle, si elle est respectée ou non et un message détaillé
     */
    public static Regle testRegle7(ArrayList<EmployeProjet> employe_projets) {
        Regle regle;
        String message;
        int minutes_travaillees_bureau = 0;
        int temps_requis_en_minutes = 60*4;
        int numero_employe = employe_projets.get(0).getNumeroEmploye();

        if (numero_employe >= 1000) {
            message = "Il s'agit d'un employé régulier qui n'est pas assujetti a cette règle.";
            regle = new Regle(7, true, message);
        } else {
            for (EmployeProjet emp_p : employe_projets) {
                if (emp_p.getNumeroProjet() <= 900 && emp_p.getJourDeSemaineTravaille() >= 1 
                    && emp_p.getJourDeSemaineTravaille() <= 5){
                    minutes_travaillees_bureau += emp_p.getTempsTravail();
                }
            }
            if (minutes_travaillees_bureau < temps_requis_en_minutes) {
                message = "L'employé a travaillé moins de 4 heures au bureau cette semaine durant les jours ouvrables.";
                regle = new Regle(7, false, message);
            } else {
               message = "L'employé a travaillée 4 heures ou plus au bureau cette semaine durant les jours ouvrables."; 
               regle = new Regle(7, true, message);
            }
        }

        return regle; 
    }
}