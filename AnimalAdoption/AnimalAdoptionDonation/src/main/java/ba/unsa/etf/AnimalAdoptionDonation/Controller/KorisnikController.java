package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Exception.ResourceNotFoundException;
import ba.unsa.etf.AnimalAdoptionDonation.Service.KorisnikService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
    public List<KorisnikDTOBO> getAllKorisnici() {
        return korisnikService.getAllKorisnici();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKorisnikById(@PathVariable int id) {
        Optional<KorisnikDTOBO> korisnikDTOBO = korisnikService.getKorisnikById(id);

        if (korisnikDTOBO.isPresent()) {
            return ResponseEntity.ok(korisnikDTOBO.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Korisnik sa ID " + id + " ne postoji."));
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createKorisnik(@Valid @RequestBody Korisnik korisnik) {
        Optional<KorisnikDTOBO> createdKorisnik = Optional.ofNullable(korisnikService.createKorisnik(korisnik));

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



    //omogucava unos samo odredjenih polja za update korisnika

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchKorisnik(@PathVariable int id, @RequestBody JsonPatch patch) {
        try {
            KorisnikDTOBO patchedKorisnik = korisnikService.applyPatchToKorisnik(id, patch);
            return ResponseEntity.ok(patchedKorisnik);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid patch request: " + e.getMessage()));
        }
    }

}



