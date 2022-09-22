package com.groupe1.feuilletemps.data;

import org.springframework.data.repository.CrudRepository;

import com.groupe1.feuilletemps.modeles.Projet;

public interface ProjetRepository extends CrudRepository<Projet, Long>{
    
}
