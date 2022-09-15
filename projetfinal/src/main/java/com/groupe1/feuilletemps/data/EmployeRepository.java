package com.groupe1.feuilletemps.data;

import org.springframework.data.repository.CrudRepository;

import com.groupe1.feuilletemps.Employe;

public interface EmployeRepository extends CrudRepository<Employe, Long> {
    
}
