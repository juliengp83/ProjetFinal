package com.groupe1.feuilletemps.data;

import org.springframework.data.repository.CrudRepository;

import com.groupe1.feuilletemps.modeles.Employe;

public interface EmployeRepository extends CrudRepository<Employe, Long> {
    Employe findByNomUtilisateur(String nomUtilisateur);
}
