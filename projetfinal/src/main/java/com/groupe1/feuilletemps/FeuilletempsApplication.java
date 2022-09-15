package com.groupe1.feuilletemps;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.groupe1.feuilletemps.data.EmployeRepository;
import com.groupe1.feuilletemps.data.ProjetRepository;

@SpringBootApplication
public class FeuilletempsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeuilletempsApplication.class, args);
	}

	@Bean
	public CommandLineRunner data_loader(EmployeRepository repo_emp, ProjetRepository repo_proj) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Projet projet1 = new Projet(999L, "Tennis");
				Projet projet2 = new Projet(500L, "Bowling");
				Projet projet3 = new Projet(100L, "Tetris");

				repo_proj.save(projet1);
				repo_proj.save(projet2);
				repo_proj.save(projet3);

				Employe employe1 = new Employe(1L, "Alex", "Pom", "1337 Avenue", "APom", "haxor");
				Employe employe2 = new Employe(2L, "Eric", "Marquis", "666 Avenue", "EMarquis", "password");
				Employe employe3 = new Employe(3L, "Julien", "GPoirier", "1337 Avenue", "JGpoirer", "jpeg");
				Employe employe4 = new Employe(4L, "Emile", "Tremblay", "1010 Avenue", "ETremblay", "perdu");

				employe1.ajouterProjet(projet3);
				employe1.ajouterProjet(projet1);

				employe2.ajouterProjet(projet2);
				employe2.ajouterProjet(projet3);

				employe3.ajouterProjet(projet1);
				employe3.ajouterProjet(projet2);
				employe3.ajouterProjet(projet3);

				employe4.ajouterProjet(projet3);
				employe4.ajouterProjet(projet1);

				repo_emp.save(employe1);
				repo_emp.save(employe2);
				repo_emp.save(employe3);
				repo_emp.save(employe4);
			}
		};
	}
}
