package ba.unsa.etf.AnimalAdoptionPet.service;

import ba.unsa.etf.AnimalAdoptionPet.Entity.OmiljeniLjubimci;
import ba.unsa.etf.AnimalAdoptionPet.Repository.OmiljeniLjubimciRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OmiljeniLjubimciService {
    private final OmiljeniLjubimciRepository repository;

    public OmiljeniLjubimciService(OmiljeniLjubimciRepository repository) {
        this.repository = repository;
    }

    public OmiljeniLjubimci save(OmiljeniLjubimci ljubimac) {
        return repository.save(ljubimac);
    }

    public List<OmiljeniLjubimci> getByKorisnikId(UUID korisnikId) {
        return repository.findByKorisnikID(korisnikId);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<OmiljeniLjubimci> getAll() {
        return repository.findAll();
    }
}
