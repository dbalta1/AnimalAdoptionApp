package ba.unsa.etf.AnimalAdoptionUser.controller;

import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikCreateDTO;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikUpdateDTO;
import ba.unsa.etf.AnimalAdoptionUser.service.KorisnikService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping
    public List<KorisnikDTO> getAllUsers() {
        return korisnikService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<KorisnikDTO> getUserById(@PathVariable int id) {
        return korisnikService.getUserById(id);
    }

    @GetMapping("/uuid/{korisnikId}")
    public Optional<KorisnikDTO> getUserByUuid(@PathVariable UUID korisnikId) {
        return korisnikService.getUserByUuid(korisnikId);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody Korisnik korisnikCreateDTO) {
        return korisnikService.createUser(korisnikCreateDTO);
    }

    @PutMapping("/{id}")
    public KorisnikDTO updateUser(@PathVariable int id, @Valid @RequestBody Korisnik korisnikUpdateDTO) {
        return korisnikService.updateUser(id, korisnikUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return korisnikService.deleteUser(id);
    }
}


