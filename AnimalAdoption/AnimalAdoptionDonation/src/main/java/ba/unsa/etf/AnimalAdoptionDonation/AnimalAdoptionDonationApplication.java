package ba.unsa.etf.AnimalAdoptionDonation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AnimalAdoptionDonationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalAdoptionDonationApplication.class, args);

		// OVO JE NOVI KLONIRANI REPO

	}

}
