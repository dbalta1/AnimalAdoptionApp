package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Service.KorisnikService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping
    public List<KorisnikDTO> getAllKorisnici() {
        return korisnikService.getAllKorisnici();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKorisnikById(@PathVariable int id) {
        Optional<KorisnikDTO> korisnikDTO = korisnikService.getKorisnikById(id);

        if (korisnikDTO.isPresent()) {
            return ResponseEntity.ok(korisnikDTO.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Korisnik sa ID " + id + " ne postoji."));
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createKorisnik(@Valid @RequestBody Korisnik korisnik) {
        Optional<KorisnikDTO> createdKorisnik = Optional.ofNullable(korisnikService.createKorisnik(korisnik));

        if (createdKorisnik.isPresent()) {
            return ResponseEntity.status(201).body(Map.of("message", "Korisnik je uspjesno kreiran.", "korisnik", createdKorisnik.get()));
        } else {
            return ResponseEntity.status(400).body(Map.of("message", "Uneseni podaci nisu validni."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateKorisnik(@PathVariable int id, @RequestBody KorisnikDTOBO korisnikDTO) {
        Optional<Korisnik> updatedKorisnik = korisnikService.updateKorisnik(id, korisnikDTO);

        if (updatedKorisnik.isPresent()) {
            return ResponseEntity.ok(updatedKorisnik.get()); // Vraća ažuriranog korisnika
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Korisnik sa ID " + id + " ne postoji."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKorisnik(@PathVariable int id) {
        if (korisnikService.deleteKorisnik(id)) {
            return ResponseEntity.ok(Map.of("message", "Korisnik sa ID " + id + " je uspjesno obrisan."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Korisnik sa ID " + id + " ne postoji."));
        }
    }

}



