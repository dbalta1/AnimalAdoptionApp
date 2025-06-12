package ba.unsa.etf.AnimalAdoptionEducation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"ba.unsa.etf.AnimalAdoptionEducation"})
@EnableFeignClients(basePackages = {"ba.unsa.etf.AnimalAdoptionEducation.client"})
public class AnimalAdoptionEducationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalAdoptionEducationApplication.class, args);
	}

}
