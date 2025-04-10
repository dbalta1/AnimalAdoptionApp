package ba.unsa.etf.AnimalAdoptionPet.controller;
import ba.unsa.etf.AnimalAdoptionPet.Entity.OmiljeniLjubimci;
import ba.unsa.etf.AnimalAdoptionPet.service.OmiljeniLjubimciService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/omiljeni-ljubimci")
public class OmiljeniLjubimciController {

    private final OmiljeniLjubimciService service;

    public OmiljeniLjubimciController(OmiljeniLjubimciService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OmiljeniLjubimci> create(@RequestBody OmiljeniLjubimci ljubimac) {
        return ResponseEntity.ok(service.save(ljubimac));
    }

    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<OmiljeniLjubimci>> getByKorisnik(@PathVariable UUID korisnikId) {
        return ResponseEntity.ok(service.getByKorisnikId(korisnikId));
    }

    @GetMapping
    public ResponseEntity<List<OmiljeniLjubimci>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
