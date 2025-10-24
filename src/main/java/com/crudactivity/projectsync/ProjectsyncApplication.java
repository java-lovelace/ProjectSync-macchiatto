package com.crudactivity.projectsync;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ProjectsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectsyncApplication.class, args);
	}

}

