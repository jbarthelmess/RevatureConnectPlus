package dev.group1.RevatureConnectPlus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"dev.group1.repos"})
@EntityScan(basePackages = {"dev.group1.entities"})
@SpringBootApplication
public class RevatureConnectPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevatureConnectPlusApplication.class, args);
	}

}
