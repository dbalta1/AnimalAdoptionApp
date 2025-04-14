package ba.unsa.etf.AnimalAdoptionDonation.Controller;


import ba.unsa.etf.AnimalAdoptionDonation.DTO.AkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.VolonterAkcija;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.KorisnikService;
import ba.unsa.etf.AnimalAdoptionDonation.Service.VolonterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/volonteri")
public class VolonterController {
    @Autowired
    private VolonterService volonterService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private VolonterRepository volonterRepository;

    @GetMapping
    public ResponseEntity<List<VolonterDTOBO>>getAllVolonteri() {
        return ResponseEntity.ok(volonterService.getSveVolontere());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVolonterById(@PathVariable int id) {
        Optional<VolonterDTOBO> volonter = volonterService.getVolonterById(id);

        if (volonter.isPresent()) {
            return ResponseEntity.ok(volonter.get());
        }
        return ResponseEntity.status(404).body(Map.of("message", "Volonter sa ID " + id + " ne postoji."));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> dodajVolontera(@Valid @RequestBody VolonterDTOBO volonterDTO) {
        Optional<Korisnik> korisnikOpt = korisnikRepository.findById(volonterDTO.getKorisnikId());

        if (!korisnikOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Korisnik sa ID " + volonterDTO.getKorisnikId() + " ne postoji."));
        }

        Volonter volonter = new Volonter();
        volonter.setDatumVolontiranja(volonterDTO.getDatumVolontiranja());
        volonter.setKorisnik(korisnikOpt.get());

        volonterService.createVolonter(volonter);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Volonter uspjesno kreiran."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVolonter(@PathVariable int id) {
        boolean deleted = volonterService.deleteVolonter(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Volonter uspjesno obrisan!"));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Volonter sa ID " + id + " ne postoji."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateVolonter(@PathVariable int id, @RequestBody VolonterDTOBO volonterDTOBO) {
        Volonter updatedVolonter = volonterService.updateVolonter(id, volonterDTOBO);

        if (updatedVolonter != null) {
            return ResponseEntity.ok(Map.of("message", "Volonter uspjesno azuriran!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Volonter sa ID " + id + " nije pronadjen."));
        }
    }

}
