package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<KorisnikDTO> getAllKorisnici() {
        return korisnikRepository.findAll().stream()
                .map(korisnik -> modelMapper.map(korisnik, KorisnikDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<KorisnikDTO> getKorisnikById(int id) {
        return korisnikRepository.findById(id)
                .map(korisnik -> modelMapper.map(korisnik, KorisnikDTO.class));
    }

    public KorisnikDTO createKorisnik(Korisnik korisnik) {
        korisnik.setKorisnikId(UUID.randomUUID()); // Generišemo UUID
        Korisnik savedKorisnik = korisnikRepository.save(korisnik); // Spasiti korisnika u bazu
        return modelMapper.map(savedKorisnik, KorisnikDTO.class); // Vratiti DTO za korisnika
    }

    public Optional<Korisnik> updateKorisnik(int id, KorisnikDTOBO korisnikDTO) {
        Optional<Korisnik> optionalKorisnik = korisnikRepository.findById(id);

        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();

            // Ažuriraj samo ne-null atribute iz DTO-a
            if (korisnikDTO.getUloga() != null) korisnik.setUloga(korisnikDTO.getUloga());
            if (korisnikDTO.getIme() != null) korisnik.setIme(korisnikDTO.getIme());
            if (korisnikDTO.getPrezime() != null) korisnik.setPrezime(korisnikDTO.getPrezime());
            if (korisnikDTO.getUsername() != null) korisnik.setUsername(korisnikDTO.getUsername());
            if (korisnikDTO.getPassword() != null) korisnik.setPassword(korisnikDTO.getPassword());
            if (korisnikDTO.getEmail() != null) korisnik.setEmail(korisnikDTO.getEmail());
            if (korisnikDTO.getTelefon() != null) korisnik.setTelefon(korisnikDTO.getTelefon());
            if (korisnikDTO.getSpol() != null) korisnik.setSpol(korisnikDTO.getSpol());
            if (korisnikDTO.getGodine() > 0) korisnik.setGodine(korisnikDTO.getGodine());
            if (korisnikDTO.getAdresa() != null) korisnik.setAdresa(korisnikDTO.getAdresa());

            korisnikRepository.save(korisnik);
            return Optional.of(korisnik);
        }
        return Optional.empty();
    }

    public boolean deleteKorisnik(int id) {
        if (korisnikRepository.existsById(id)) {
            korisnikRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
