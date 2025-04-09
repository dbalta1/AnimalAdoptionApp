package ba.unsa.etf.AnimalAdoptionUser.service;

import ba.unsa.etf.AnimalAdoptionUser.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Uloga;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikCreateDTO;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikUpdateDTO;
import ba.unsa.etf.AnimalAdoptionUser.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<KorisnikDTO> getAllUsers() {
        return korisnikRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<KorisnikDTO> getUserById(int id) {
        return korisnikRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<KorisnikDTO> getUserByUuid(UUID korisnikId) {
        return korisnikRepository.findByKorisnikId(korisnikId).map(this::convertToDTO);
    }

    public ResponseEntity<Object> createUser(Korisnik korisnikCreateDTO) {
        // Validacija
        // Možeš dodati validaciju pomoću BindingResult ako želiš dodatne provere.
        if (korisnikCreateDTO.getIme() == null || korisnikCreateDTO.getPrezime() == null) {
            return ResponseEntity.badRequest().body("Ime i Prezime su obavezna polja.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikCreateDTO.getIme());
        korisnik.setPrezime(korisnikCreateDTO.getPrezime());
        korisnik.setEmail(korisnikCreateDTO.getEmail());
        korisnik.setUsername(korisnikCreateDTO.getUsername());
        korisnik.setPassword(korisnikCreateDTO.getPassword());
        korisnik.setTelefon(korisnikCreateDTO.getTelefon());
        korisnik.setSpol(korisnikCreateDTO.getSpol());
        korisnik.setGodine(korisnikCreateDTO.getGodine());
        korisnik.setAdresa(korisnikCreateDTO.getAdresa());
        korisnik.setUloga(korisnikCreateDTO.getUloga());

        korisnik.setKorisnikId(UUID.randomUUID());

        Korisnik savedUser = korisnikRepository.save(korisnik);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
    }

    public KorisnikDTO updateUser(int id, Korisnik korisnikUpdateDTO) {
        Korisnik korisnik = korisnikRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Korisnik nije pronađen"));

        if (korisnikUpdateDTO.getIme() != null) korisnik.setIme(korisnikUpdateDTO.getIme());
        if (korisnikUpdateDTO.getPrezime() != null) korisnik.setPrezime(korisnikUpdateDTO.getPrezime());
        if (korisnikUpdateDTO.getEmail() != null) korisnik.setEmail(korisnikUpdateDTO.getEmail());
        if (korisnikUpdateDTO.getUsername() != null) korisnik.setUsername(korisnikUpdateDTO.getUsername());
        if (korisnikUpdateDTO.getPassword() != null) korisnik.setPassword(korisnikUpdateDTO.getPassword());
        if (korisnikUpdateDTO.getTelefon() != null) korisnik.setTelefon(korisnikUpdateDTO.getTelefon());
        if (korisnikUpdateDTO.getSpol() != null) korisnik.setSpol(korisnikUpdateDTO.getSpol());
        korisnik.setGodine(korisnikUpdateDTO.getGodine());
        if (korisnikUpdateDTO.getAdresa() != null) korisnik.setAdresa(korisnikUpdateDTO.getAdresa());
        if (korisnikUpdateDTO.getUloga() != null) korisnik.setUloga(korisnikUpdateDTO.getUloga());

        korisnikRepository.save(korisnik);

        return convertToDTO(korisnik);
    }

    public ResponseEntity<String> deleteUser(int id) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);
        if (korisnik.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID " + id + " nije pronađen.");
        }

        korisnikRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Korisnik sa ID " + id + " je uspešno obrisan.");
    }

    private KorisnikDTO convertToDTO(Korisnik korisnik) {
        KorisnikDTO dto = new KorisnikDTO();
        dto.setId(korisnik.getId());
        dto.setIme(korisnik.getIme());
        dto.setPrezime(korisnik.getPrezime());
        dto.setEmail(korisnik.getEmail());
        dto.setKorisnikId(korisnik.getKorisnikId());
        dto.setUsername(korisnik.getUsername());
        dto.setTelefon(korisnik.getTelefon());
        dto.setSpol(korisnik.getSpol());
        dto.setGodine(korisnik.getGodine());
        dto.setAdresa(korisnik.getAdresa());
        dto.setUloga(korisnik.getUloga());
        return dto;
    }
}
