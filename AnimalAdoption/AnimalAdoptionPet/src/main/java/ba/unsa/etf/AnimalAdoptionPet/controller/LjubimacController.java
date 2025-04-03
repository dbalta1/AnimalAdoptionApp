package ba.unsa.etf.AnimalAdoptionPet.controller;

import ba.unsa.etf.AnimalAdoptionPet.dto.LjubimacCreateDTO;
import ba.unsa.etf.AnimalAdoptionPet.dto.LjubimacDTO;
import ba.unsa.etf.AnimalAdoptionPet.service.LjubimacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/ljubimci")
public class LjubimacController {
    private final LjubimacService ljubimacService;

    public LjubimacController(LjubimacService ljubimacService) {
        this.ljubimacService = ljubimacService;
    }

    @GetMapping
    public ResponseEntity<List<LjubimacDTO>> getAllLjubimci() {
        return ResponseEntity.ok(ljubimacService.getAllLjubimci());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LjubimacDTO> getLjubimacById(@PathVariable int id) {
        return ResponseEntity.ok(ljubimacService.getLjubimacById(id));
    }

    @PostMapping
    public ResponseEntity<LjubimacDTO> createLjubimac(@RequestBody LjubimacCreateDTO ljubimacCreateDTO) {
        return ResponseEntity.ok(ljubimacService.createLjubimac(ljubimacCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LjubimacDTO> updateLjubimac(@PathVariable int id, @RequestBody LjubimacDTO ljubimacDTO) {
        return ResponseEntity.ok(ljubimacService.updateLjubimac(id, ljubimacDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLjubimac(@PathVariable int id) {
        ljubimacService.deleteLjubimac(id);
        return ResponseEntity.noContent().build();
    }
}


