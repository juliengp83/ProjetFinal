package com.groupe1.feuilletemps.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.groupe1.feuilletemps.modeles.FeuilleDeTemps;

public interface FeuilleDeTempsRepository extends CrudRepository<FeuilleDeTemps, Long> {

    @Query(value = "SELECT * FROM FEUILLE_TEMPS WHERE TO_CHAR(DATE_SEMAINE, 'WW') = ?1", nativeQuery = true)
    Iterable<FeuilleDeTemps> getFeuillesDeTempsByWeekOfYear(int weekOfYear);
    
}
