package com.groupe1.feuilletemps.modeles;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class FeuilleDeTemps {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_feuilledetemps")
    @SequenceGenerator(name = "gen_feuilledetemps", sequenceName = "SEQ_FEUILLEDETEMPS", allocationSize = 1)
    @OrderColumn
    private Long idFeuilleTemps;

    @ManyToOne()
    private Employe employe;

    private Date dateSemaine;

    @Type(type="true_false")
    @NotNull
    private boolean estValidee;
    
    @Type(type="true_false")
    @NotNull
    private boolean estApprouvee;

    @OneToMany()
    private List<EntreeProjet> entreesProjets; 

    public FeuilleDeTemps(Employe employe, Date dateSemaine, boolean estValidee, boolean estApprouvee) {
        this.employe = employe;
        this.dateSemaine = dateSemaine;
        this.estValidee = estValidee;
        this.estApprouvee = estApprouvee;
    }
}
