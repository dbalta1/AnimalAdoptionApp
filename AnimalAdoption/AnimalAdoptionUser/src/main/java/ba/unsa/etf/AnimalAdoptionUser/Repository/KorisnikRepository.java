package ba.unsa.etf.AnimalAdoptionUser.Repository;

import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByKorisnikId(UUID korisnikId);
    Optional<Korisnik> findByEmail(String email);
}

