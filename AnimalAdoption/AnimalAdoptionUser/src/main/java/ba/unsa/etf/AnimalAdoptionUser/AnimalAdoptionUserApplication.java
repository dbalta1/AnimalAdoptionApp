package ba.unsa.etf.AnimalAdoptionUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AnimalAdoptionUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalAdoptionUserApplication.class, args);
	}

}
