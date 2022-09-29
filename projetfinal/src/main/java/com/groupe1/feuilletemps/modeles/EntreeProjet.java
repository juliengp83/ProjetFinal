package com.groupe1.feuilletemps.modeles;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ENTREE_PROJET")
public class EntreeProjet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_entreeprojet")
    @SequenceGenerator(name = "gen_entreeprojet", sequenceName = "SEQ_ENTREEPROJET", allocationSize = 1)
    @OrderColumn
    private Long id;

    @OneToOne
    private Projet projet;

    private int tempsTravaille;

    private Date dateTravaille;

    public EntreeProjet(Projet projet, int tempsTravaille, Date dateTravaille) {
        this.projet = projet;
        this.tempsTravaille = tempsTravaille;
        this.dateTravaille = dateTravaille;
    }
}
