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

    public KorisnikDTO createUser(KorisnikCreateDTO korisnikCreateDTO) {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikCreateDTO.getIme());
        korisnik.setPrezime(korisnikCreateDTO.getPrezime());
        korisnik.setEmail(korisnikCreateDTO.getEmail());
        korisnik.setUsername(korisnikCreateDTO.getUsername());
        korisnik.setPassword(korisnikCreateDTO.getPassword());
        korisnik.setTelefon(korisnikCreateDTO.getTelefon());
        korisnik.setSpol(Spol.valueOf(korisnikCreateDTO.getSpol()));
        korisnik.setGodine(korisnikCreateDTO.getGodine());
        korisnik.setAdresa(korisnikCreateDTO.getAdresa());
        korisnik.setUloga(Uloga.valueOf(korisnikCreateDTO.getUloga()));

        korisnik.setKorisnikId(UUID.randomUUID());

        Korisnik savedUser = korisnikRepository.save(korisnik);

        return convertToDTO(savedUser);
    }

    public KorisnikDTO updateUser(int id, KorisnikUpdateDTO korisnikUpdateDTO) {
        Korisnik korisnik = korisnikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

        if (korisnikUpdateDTO.getIme() != null) korisnik.setIme(korisnikUpdateDTO.getIme());
        if (korisnikUpdateDTO.getPrezime() != null) korisnik.setPrezime(korisnikUpdateDTO.getPrezime());
        if (korisnikUpdateDTO.getEmail() != null) korisnik.setEmail(korisnikUpdateDTO.getEmail());
        if (korisnikUpdateDTO.getUsername() != null) korisnik.setUsername(korisnikUpdateDTO.getUsername());
        if (korisnikUpdateDTO.getPassword() != null) korisnik.setPassword(korisnikUpdateDTO.getPassword());
        if (korisnikUpdateDTO.getTelefon() != null) korisnik.setTelefon(korisnikUpdateDTO.getTelefon());
        if (korisnikUpdateDTO.getSpol() != null) korisnik.setSpol(Spol.valueOf(korisnikUpdateDTO.getSpol()));
        if (korisnikUpdateDTO.getGodine() != null) korisnik.setGodine(korisnikUpdateDTO.getGodine());
        if (korisnikUpdateDTO.getAdresa() != null) korisnik.setAdresa(korisnikUpdateDTO.getAdresa());
        if (korisnikUpdateDTO.getUloga() != null) korisnik.setUloga(Uloga.valueOf(korisnikUpdateDTO.getUloga()));

        korisnikRepository.save(korisnik);

        return convertToDTO(korisnik);
    }

    public void deleteUser(int id) {
        korisnikRepository.deleteById(id);
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
