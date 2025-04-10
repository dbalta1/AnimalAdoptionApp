package ba.unsa.etf.AnimalAdoptionPet.Repository;

import ba.unsa.etf.AnimalAdoptionPet.Entity.OmiljeniLjubimci;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface OmiljeniLjubimciRepository extends JpaRepository<OmiljeniLjubimci, Integer> {
    List<OmiljeniLjubimci> findByKorisnikID(UUID korisnikID);
}
