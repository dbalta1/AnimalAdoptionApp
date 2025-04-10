package ba.unsa.etf.AnimalAdoptionPet.controller;

import ba.unsa.etf.AnimalAdoptionPet.Entity.ZahtjevZaUdomljavanje;
import ba.unsa.etf.AnimalAdoptionPet.service.ZahtjevZaUdomljavanjeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/zahtjevi")
public class ZahtjevZaUdomljavanjeController {

    private final ZahtjevZaUdomljavanjeService service;

    public ZahtjevZaUdomljavanjeController(ZahtjevZaUdomljavanjeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ZahtjevZaUdomljavanje> create(@RequestBody ZahtjevZaUdomljavanje zahtjev) {
        return ResponseEntity.ok(service.save(zahtjev));
    }

    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<ZahtjevZaUdomljavanje>> getByKorisnik(@PathVariable UUID korisnikId) {
        return ResponseEntity.ok(service.getByKorisnikId(korisnikId));
    }

    @GetMapping
    public ResponseEntity<List<ZahtjevZaUdomljavanje>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
