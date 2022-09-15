package com.groupe1.feuilletemps;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employe {

    @Id
    private Long employe_id;

    @NotNull
    private String prenom;

    @NotNull
    private String nom;

    @NotNull
    private String addresse;

    @NotNull
    private String nom_utilisateur;

    @NotNull
    private String mot_de_passe;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EmployeProjet", 
            joinColumns = {
                @JoinColumn(name = "employe_id") }, 
            inverseJoinColumns = {
                @JoinColumn(name = "numero_projet") }
    )
    private Set<Projet> projets = new HashSet<Projet>();

    public Employe(Long employe_id, String prenom, String nom, String addresse, String nom_utilisateur, String mot_de_passe) {
        this.employe_id = employe_id;
        this.prenom = prenom;
        this.nom = nom;
        this.addresse = addresse;
        this.nom_utilisateur = nom_utilisateur;
        this.mot_de_passe = mot_de_passe;
    }

    public void ajouterProjet(Projet projet){
        this.projets.add(projet);
    }

}
