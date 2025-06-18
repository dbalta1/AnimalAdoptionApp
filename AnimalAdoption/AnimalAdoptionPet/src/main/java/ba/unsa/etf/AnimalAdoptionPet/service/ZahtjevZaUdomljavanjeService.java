package ba.unsa.etf.AnimalAdoptionPet.service;

import ba.unsa.etf.AnimalAdoptionPet.Entity.Ljubimac;
import ba.unsa.etf.AnimalAdoptionPet.Entity.StatusZahtjeva;
import ba.unsa.etf.AnimalAdoptionPet.Entity.ZahtjevZaUdomljavanje;
import ba.unsa.etf.AnimalAdoptionPet.dto.ZahtjevZaUdomljavanjeDTO;
import ba.unsa.etf.AnimalAdoptionPet.repository.ZahtjevZaUdomljavanjeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
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

    public ZahtjevZaUdomljavanje create(ZahtjevZaUdomljavanjeDTO dto) {
        Ljubimac ljubimac = new Ljubimac();
        ljubimac.setId(dto.getLjubimacId());

        ZahtjevZaUdomljavanje zahtjev = new ZahtjevZaUdomljavanje();
        zahtjev.setLjubimac(ljubimac);
        zahtjev.setKorisnikID(dto.getKorisnikId());
        zahtjev.setStatusZahtjeva(StatusZahtjeva.NA_CEKANJU);
        zahtjev.setDodatnaNapomena(dto.getDodatnaNapomena());
        zahtjev.setDatumOdobrenjaZahtjeva(dto.getDatumOdobrenjaZahtjeva());
        zahtjev.setDatumPodnosenjaZahtjeva(dto.getDatumPodnosenjaZahtjeva());

        return repository.save(zahtjev);
    }

}
