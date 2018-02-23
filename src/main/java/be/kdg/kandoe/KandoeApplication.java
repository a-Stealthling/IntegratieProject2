package be.kdg.kandoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@ComponentScan
@EntityScan
public class KandoeApplication {
	public static void main(String[] args) {
		SpringApplication.run(KandoeApplication.class, args);
	}
}
