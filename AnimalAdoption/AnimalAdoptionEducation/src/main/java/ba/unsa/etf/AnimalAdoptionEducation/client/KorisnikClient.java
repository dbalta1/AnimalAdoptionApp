package ba.unsa.etf.AnimalAdoptionEducation.client;

import ba.unsa.etf.AnimalAdoptionEducation.dto.KorisnikDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AnimalAdoptionUser")


public interface KorisnikClient {

    @GetMapping("/korisnici/{id}")
    KorisnikDTO getUserById(@PathVariable("id") int id);
}