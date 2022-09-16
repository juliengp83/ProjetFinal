package com.groupe1.feuilletemps;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Projet {
    
    @Id
    private Long numero_projet;

    private String nom_projet;

    @ManyToMany(mappedBy = "projets")
    private Set<Employe> employes;

    public Projet(){
        
    }

    public Projet(Long numero_projet, String nom_projet) {
        this.numero_projet = numero_projet;
        this.nom_projet = nom_projet;
    }
    

    public Projet(Long numero_projet, String nom_projet, Set<Employe> employes) {
        this.numero_projet = numero_projet;
        this.nom_projet = nom_projet;
        this.employes = employes;
    }

}
