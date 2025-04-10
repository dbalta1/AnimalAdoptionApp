package ba.unsa.etf.AnimalAdoptionPet.service;

import ba.unsa.etf.AnimalAdoptionPet.Entity.OmiljeniLjubimci;
import ba.unsa.etf.AnimalAdoptionPet.repository.OmiljeniLjubimciRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class OmiljeniLjubimciService {

    private final OmiljeniLjubimciRepository repository;

    public OmiljeniLjubimciService(OmiljeniLjubimciRepository repository) {
        this.repository = repository;
    }

    public List<OmiljeniLjubimci> getAll() {
        return repository.findAll();
    }

    public OmiljeniLjubimci getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Omiljeni ljubimac nije pronađen."));
    }

    public List<OmiljeniLjubimci> getByKorisnikId(UUID korisnikId) {
        return repository.findByKorisnikID(korisnikId);
    }

    public OmiljeniLjubimci create(OmiljeniLjubimci ljubimac) {
        if (ljubimac.getKorisnikID() == null || ljubimac.getLjubimac() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisnik ID i ljubimac su obavezni.");
        }
        return repository.save(ljubimac);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Omiljeni ljubimac nije pronađen.");
        }
        repository.deleteById(id);
    }
}
