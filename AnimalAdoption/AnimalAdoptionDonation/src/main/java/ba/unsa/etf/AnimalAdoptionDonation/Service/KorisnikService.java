package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Exception.ResourceNotFoundException;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
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

    @Autowired
    private ObjectMapper objectMapper;

    public List<KorisnikDTOBO> getAllKorisnici() {
        return korisnikRepository.findAll().stream()
                .map(korisnik -> modelMapper.map(korisnik, KorisnikDTOBO.class))
                .collect(Collectors.toList());
    }

    public Optional<KorisnikDTOBO> getKorisnikById(int id) {
        return korisnikRepository.findById(id)
                .map(korisnik -> modelMapper.map(korisnik, KorisnikDTOBO.class));
    }

    public KorisnikDTOBO createKorisnik(Korisnik korisnik) {
        korisnik.setKorisnikId(UUID.randomUUID()); // Generišemo UUID
        Korisnik savedKorisnik = korisnikRepository.save(korisnik); // Spasiti korisnika u bazu
        return modelMapper.map(savedKorisnik, KorisnikDTOBO.class); // Vratiti DTO za korisnika
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
            //if (korisnikDTO.getPassword() != null) korisnik.setPassword(korisnikDTO.getPassword());
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


// ODAVDE DODATO ZA PATCH


    private KorisnikDTOBO convertToDto(Korisnik korisnik) {
        KorisnikDTOBO dto = new KorisnikDTOBO();
        dto.setId(korisnik.getId());
        dto.setKorisnikId(korisnik.getKorisnikId());
        dto.setIme(korisnik.getIme());
        dto.setPrezime(korisnik.getPrezime());
        dto.setEmail(korisnik.getEmail());
        dto.setTelefon(korisnik.getTelefon());
        dto.setAdresa(korisnik.getAdresa());
        dto.setSpol(korisnik.getSpol());
        dto.setGodine(korisnik.getGodine());
        dto.setUloga(korisnik.getUloga());
        return dto;
    }

    @Transactional
    public KorisnikDTOBO applyPatchToKorisnik(int id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Korisnik korisnik = korisnikRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik not found with id: " + id));

        // Convert the Korisnik to JsonNode
        JsonNode patched = patch.apply(objectMapper.convertValue(korisnik, JsonNode.class));

        // Convert the JsonNode back to Korisnik
        Korisnik patchedKorisnik = objectMapper.treeToValue(patched, Korisnik.class);

        // Save the updated Korisnik
        korisnikRepository.save(patchedKorisnik);

        return modelMapper.map(patchedKorisnik, KorisnikDTOBO.class);
    }

}
