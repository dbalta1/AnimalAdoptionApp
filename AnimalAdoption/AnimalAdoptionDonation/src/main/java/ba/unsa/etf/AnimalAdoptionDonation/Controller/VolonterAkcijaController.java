package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterAkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Service.VolonterAkcijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/volonterAkcija")
public class VolonterAkcijaController {
    @Autowired
    private VolonterAkcijaService volonterAkcijaService;

    @GetMapping
    public List<VolonterAkcijaDTOBO> getAllVolonterAkcije() {
        return volonterAkcijaService.getAllVolonterAkcije();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolonterAkcijaDTOBO> getVolonterAkcijaById(@PathVariable int id) {
        Optional<VolonterAkcijaDTOBO> volonterAkcija = volonterAkcijaService.getVolonterAkcijaById(id);
        return volonterAkcija.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*@PostMapping
    public VolonterAkcijaDTOBO createVolonterAkcija(@RequestBody VolonterAkcijaDTOBO volonterAkcijaDTO) {
        return volonterAkcijaService.saveVolonterAkcija(volonterAkcijaDTO);
    }*/

    @PostMapping
    public ResponseEntity<?> prijaviVolontera(@RequestBody VolonterAkcijaDTOBO dto) {
        System.out.println("Primljen zahtjev za prijavu: " + dto); // LOG
        try {
            VolonterAkcijaDTOBO result = volonterAkcijaService.saveVolonterAkcija(dto);
            return ResponseEntity.ok(Map.of("message", "Uspješno ste se prijavili za volontiranje"));
        } catch (Exception e) {
            System.err.println("Greška pri prijavi: " + e.getMessage()); // LOG
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolonterAkcija(@PathVariable int id) {
        volonterAkcijaService.deleteVolonterAkcija(id);
        return ResponseEntity.noContent().build();
    }
}