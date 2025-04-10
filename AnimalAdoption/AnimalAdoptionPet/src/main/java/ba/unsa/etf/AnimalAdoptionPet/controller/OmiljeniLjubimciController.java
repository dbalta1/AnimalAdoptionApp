package ba.unsa.etf.AnimalAdoptionPet.controller;

import ba.unsa.etf.AnimalAdoptionPet.Entity.OmiljeniLjubimci;
import ba.unsa.etf.AnimalAdoptionPet.service.OmiljeniLjubimciService;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<OmiljeniLjubimci>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OmiljeniLjubimci> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<OmiljeniLjubimci>> getByKorisnikId(@PathVariable UUID korisnikId) {
        return ResponseEntity.ok(service.getByKorisnikId(korisnikId));
    }

    @PostMapping
    public ResponseEntity<OmiljeniLjubimci> create(@RequestBody OmiljeniLjubimci ljubimac) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(ljubimac));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
