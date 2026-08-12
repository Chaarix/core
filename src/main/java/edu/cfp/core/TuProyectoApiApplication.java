package edu.cfp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TuProyectoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuProyectoApiApplication.class, args);
	}

}