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

				Projet p1000 = new Projet(1000L, "Télétravail");
				Projet p500 = new Projet(500L, "Site Web SSQ");
				Projet p100 = new Projet(100L, "Gestion de projet");

				Projet p995 = new Projet(995L, "Congé parental");
				Projet p996 = new Projet(996L, "Transport en voiture");
				Projet p997 = new Projet(997L, "Transport en commun");
				Projet p998 = new Projet(998L, "Congé férié");
				Projet p999 = new Projet(999L, "Congé de maladie");

				repo_proj.save(p1000);
				repo_proj.save(p500);
				repo_proj.save(p100);
				repo_proj.save(p995);
				repo_proj.save(p996);
				repo_proj.save(p997);
				repo_proj.save(p998);
				repo_proj.save(p999);

				Employe employe1 = new Employe(1L, "Hamdael", "Steelbeard", "hamdael@julexmile.com",
						"hamdael", AES.encrypt("hamdael123", "bBgLrINTjBINrm7"));
				Employe employe2 = new Employe(1112L, "Thralmig", "Deepbrew", "thralmig@julexmile.com",
						"thralmig", AES.encrypt("thralmig123", "bBgLrINTjBINrm7"));
				Employe employe3 = new Employe(3L, "Mundaeth", "Dragonbender", "mundaeth@julexmile.com",
						"mundaeth", AES.encrypt("mundaeth123", "bBgLrINTjBINrm7"));
				Employe employe4 = new Employe(4L, "Thromdol", "Hillbreaker", "thromdol@julexmile.com",
						"thromdol", AES.encrypt("thromdol123", "bBgLrINTjBINrm7"));
				Employe employe5 = new Employe(5L, "Valgid", "Honordigger", "valgid@julexmile.com",
						"valgid", AES.encrypt("valgid123", "bBgLrINTjBINrm7"));

				employe1.ajouterProjet(p100);
				employe1.ajouterProjet(p1000);

				employe2.ajouterProjet(p1000);
				employe2.ajouterProjet(p100);
				employe2.ajouterProjet(p995);
				employe2.ajouterProjet(p996);
				employe2.ajouterProjet(p997);
				employe2.ajouterProjet(p998);
				employe2.ajouterProjet(p999);

				employe3.ajouterProjet(p1000);
				employe3.ajouterProjet(p500);
				employe3.ajouterProjet(p100);
				employe3.ajouterProjet(p995);
				employe3.ajouterProjet(p996);
				employe3.ajouterProjet(p997);
				employe3.ajouterProjet(p998);
				employe3.ajouterProjet(p999);

				employe4.ajouterProjet(p100);
				employe4.ajouterProjet(p1000);

				employe5.ajouterProjet(p1000);
				employe5.ajouterProjet(p100);

				repo_emp.save(employe1);
				repo_emp.save(employe2);
				repo_emp.save(employe3);
				repo_emp.save(employe4);
				repo_emp.save(employe5);

				// ajout d'un employe admin et regulier juste pour tester rapidement
				// Employe admin = new Employe(1000L, "Emile", "Tremblay", "EmileX@123.com",
				// "admin",
				// AES.encrypt("", "bBgLrINTjBINrm7"));
				// admin.ajouterProjet(p1000);
				// admin.ajouterProjet(p500);
				// admin.ajouterProjet(p100);
				// admin.ajouterProjet(p995);
				// admin.ajouterProjet(p996);
				// admin.ajouterProjet(p997);
				// admin.ajouterProjet(p998);
				// admin.ajouterProjet(p999);

				// repo_emp.save(admin);
				// Employe regulier = new Employe(1001L, "Emile", "Tremblay", "3@123.com",
				// "reg",
				// AES.encrypt("", "bBgLrINTjBINrm7"));
				// regulier.ajouterProjet(p1000);
				// regulier.ajouterProjet(p500);
				// regulier.ajouterProjet(p100);
				// regulier.ajouterProjet(p995);
				// regulier.ajouterProjet(p996);
				// regulier.ajouterProjet(p997);
				// regulier.ajouterProjet(p998);
				// regulier.ajouterProjet(p999);
				// repo_emp.save(regulier);
			}

		};
	}
}
