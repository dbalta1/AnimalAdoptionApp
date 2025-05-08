package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterAkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Service.VolonterAkcijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public VolonterAkcijaDTOBO createVolonterAkcija(@RequestBody VolonterAkcijaDTOBO volonterAkcijaDTO) {
        return volonterAkcijaService.saveVolonterAkcija(volonterAkcijaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolonterAkcija(@PathVariable int id) {
        volonterAkcijaService.deleteVolonterAkcija(id);
        return ResponseEntity.noContent().build();
    }
}
