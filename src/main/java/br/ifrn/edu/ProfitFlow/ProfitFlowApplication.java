package br.ifrn.edu.ProfitFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProfitFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfitFlowApplication.class, args);
	}

}
