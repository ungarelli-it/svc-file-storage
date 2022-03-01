package it.ungarelli.svcfilestorage.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = MainApplication.BASE_PACK)
public class MainApplication {

	static final String BASE_PACK = "it.ungarelli";

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}