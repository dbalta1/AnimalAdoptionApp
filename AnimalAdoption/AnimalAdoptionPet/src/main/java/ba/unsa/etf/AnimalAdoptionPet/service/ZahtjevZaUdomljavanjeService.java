package ba.unsa.etf.AnimalAdoptionPet.service;

import ba.unsa.etf.AnimalAdoptionPet.Entity.ZahtjevZaUdomljavanje;
import ba.unsa.etf.AnimalAdoptionPet.repository.ZahtjevZaUdomljavanjeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ZahtjevZaUdomljavanjeService {
    private final ZahtjevZaUdomljavanjeRepository repository;

    public ZahtjevZaUdomljavanjeService(ZahtjevZaUdomljavanjeRepository repository) {
        this.repository = repository;
    }

    public ZahtjevZaUdomljavanje save(ZahtjevZaUdomljavanje zahtjev) {
        return repository.save(zahtjev);
    }

    public List<ZahtjevZaUdomljavanje> getByKorisnikId(UUID korisnikId) {
        return repository.findByKorisnikID(korisnikId);
    }

    public List<ZahtjevZaUdomljavanje> getAll() {
        return repository.findAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
