package com.groupe1.feuilletemps;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.groupe1.feuilletemps.data.EmployeRepository;
import com.groupe1.feuilletemps.data.ProjetRepository;
import com.groupe1.feuilletemps.modeles.Employe;
import com.groupe1.feuilletemps.modeles.Projet;
import com.groupe1.feuilletemps.utils.AES;

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
				AES.setKey("bBgLrINTjBINrm7");
				Projet projet1 = new Projet(1000L, "Tennis");
				Projet projet2 = new Projet(500L, "Bowling");
				Projet projet3 = new Projet(100L, "Tetris");
				Projet p995 = new Projet(995L, "Congé parental");
				Projet p996 = new Projet(996L, "Transport en voiture");
				Projet p997 = new Projet(997L, "Transport en commun");
				Projet p998 = new Projet(998L, "Congé férié");
				Projet p999 = new Projet(999L, "Congé de maladie");

				repo_proj.save(projet1);
				repo_proj.save(projet2);
				repo_proj.save(projet3);
				repo_proj.save(p995);
				repo_proj.save(p996);
				repo_proj.save(p997);
				repo_proj.save(p998);
				repo_proj.save(p999);

				Employe employe1 = new Employe(1L, "Alex", "Pom", "1337 Avenue", "apom",
						AES.encrypt("haxor", "bBgLrINTjBINrm7"));
				Employe employe2 = new Employe(2L, "Eric", "Marquis", "666 Avenue", "emarquis",
						AES.encrypt("123", "bBgLrINTjBINrm7"));
				Employe employe3 = new Employe(3L, "Julien", "Gelineau Poirier", "1337 Avenue", "jgpoirer",
						AES.encrypt("jpeg", "bBgLrINTjBINrm7"));
				Employe employe4 = new Employe(4L, "Emile", "Tremblay", "1010 Avenue", "etremblay",
						AES.encrypt("perdu", "bBgLrINTjBINrm7"));
				Employe employe5 = new Employe(5L, "Oleksandr", "Pleshko", "5580 Avenue", "opleshko",
						AES.encrypt("olekp", "bBgLrINTjBINrm7"));		

				employe1.ajouterProjet(projet3);
				employe1.ajouterProjet(projet1);

				employe2.ajouterProjet(projet1);
				employe2.ajouterProjet(projet3);
				employe2.ajouterProjet(p995);
				employe2.ajouterProjet(p996);
				employe2.ajouterProjet(p997);
				employe2.ajouterProjet(p998);
				employe2.ajouterProjet(p999);

				employe3.ajouterProjet(projet1);
				employe3.ajouterProjet(projet2);
				employe3.ajouterProjet(projet3);

				employe4.ajouterProjet(projet3);
				employe4.ajouterProjet(projet1);

				employe5.ajouterProjet(projet1);
				employe5.ajouterProjet(projet3);

				repo_emp.save(employe1);
				repo_emp.save(employe2);
				repo_emp.save(employe3);
				repo_emp.save(employe4);
				repo_emp.save(employe5);

				// ajout d'un employe admin et regulier juste pour tester rapidement
				Employe admin = new Employe(1000L, "Emile", "Tremblay", "2020 Avenue", "admin",
						AES.encrypt("", "bBgLrINTjBINrm7"));
				admin.ajouterProjet(projet1);
				admin.ajouterProjet(projet2);
				admin.ajouterProjet(projet3);
				repo_emp.save(admin);
				Employe regulier = new Employe(1001L, "Emile", "Tremblay", "3030 Avenue", "reg",
						AES.encrypt("", "bBgLrINTjBINrm7"));
				regulier.ajouterProjet(projet1);
				regulier.ajouterProjet(projet2);
				regulier.ajouterProjet(projet3);
				repo_emp.save(regulier);
			}
		};
	}

}
