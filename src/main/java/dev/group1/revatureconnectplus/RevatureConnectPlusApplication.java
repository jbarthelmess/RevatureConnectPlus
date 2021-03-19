package dev.group1.revatureconnectplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"dev.group1"})
@EnableJpaRepositories(basePackages = {"dev.group1.repos"})
@EntityScan(basePackages = {"dev.group1.entities"})
@SpringBootApplication
public class RevatureConnectPlusApplication {

	public static void main(String[] args) {
		// auto creating application based on the info provided...
		SpringApplication.run(RevatureConnectPlusApplication.class, args);
	}

}
