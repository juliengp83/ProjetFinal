package com.groupe1.feuilletemps.modeles;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Employe {

    @Id
    private Long employeId;

    @NotNull
    private String prenom;

    @NotNull
    private String nom;

    @NotNull
    private String courriel;

    @NotNull
    private String nomUtilisateur;

    @NotNull
    private String motDePasse;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EmployeProjet", 
            joinColumns = {
                @JoinColumn(name = "employeId") }, 
            inverseJoinColumns = {
                @JoinColumn(name = "numero_projet") }
    )
    private Set<Projet> projets;

    public Employe(){

    }

    public Employe(Long employeId, String prenom, String nom, String courriel, String nomUtilisateur, String motDePasse) {
        this.employeId = employeId;
        this.prenom = prenom;
        this.nom = nom;
        this.courriel = courriel;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.projets = new HashSet<Projet>();
    }

    public void ajouterProjet(Projet projet){
        this.projets.add(projet);
    }
}