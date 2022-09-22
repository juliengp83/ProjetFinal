package com.groupe1.feuilletemps.data;

import org.springframework.data.repository.CrudRepository;

import com.groupe1.feuilletemps.modeles.FeuilleDeTemps;

public interface FeuilleDeTempsRepository extends CrudRepository<FeuilleDeTemps, Long> {
    
}
