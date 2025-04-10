package ba.unsa.etf.AnimalAdoptionPet.Repository;

import ba.unsa.etf.AnimalAdoptionPet.Entity.ZahtjevZaUdomljavanje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface ZahtjevZaUdomljavanjeRepository extends JpaRepository<ZahtjevZaUdomljavanje, Integer> {
    List<ZahtjevZaUdomljavanje> findByKorisnikID(UUID korisnikID);
}
