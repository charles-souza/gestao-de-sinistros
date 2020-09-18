package br.com.webvirtua.gestaodesinistros;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@EnableJpaRepositories
@SpringBootApplication
public class GestaoDeSinistrosApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(GestaoDeSinistrosApplication.class, args);
	}

}
