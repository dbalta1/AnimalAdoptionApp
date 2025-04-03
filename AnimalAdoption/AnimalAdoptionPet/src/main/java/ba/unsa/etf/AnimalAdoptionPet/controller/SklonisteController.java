package ba.unsa.etf.AnimalAdoptionPet.controller;

import ba.unsa.etf.AnimalAdoptionPet.dto.SklonisteDTO;
import ba.unsa.etf.AnimalAdoptionPet.service.SklonisteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sklonista")
public class SklonisteController {

    private final SklonisteService sklonisteService;

    public SklonisteController(SklonisteService sklonisteService) {
        this.sklonisteService = sklonisteService;
    }

    @GetMapping
    public ResponseEntity<List<SklonisteDTO>> getAllSklonista() {
        return ResponseEntity.ok(sklonisteService.getAllSklonista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SklonisteDTO> getSklonisteById(@PathVariable int id) {
        return ResponseEntity.ok(sklonisteService.getSklonisteById(id));
    }

    @PostMapping
    public ResponseEntity<SklonisteDTO> createSkloniste(@Valid @RequestBody SklonisteDTO sklonisteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sklonisteService.createSkloniste(sklonisteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SklonisteDTO> updateSkloniste(@PathVariable int id, @Valid @RequestBody SklonisteDTO sklonisteDTO) {
        return ResponseEntity.ok(sklonisteService.updateSkloniste(id, sklonisteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkloniste(@PathVariable int id) {
        sklonisteService.deleteSkloniste(id);
        return ResponseEntity.noContent().build();
    }
}
