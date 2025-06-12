package ba.unsa.etf.AnimalAdoptionDonation.Client;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "animaladoptionuser")
public interface KorisnikClient {

    @GetMapping("/korisnici/{id}")
    KorisnikDTOBO getKorisnikById(@PathVariable("id") Long id);
}
