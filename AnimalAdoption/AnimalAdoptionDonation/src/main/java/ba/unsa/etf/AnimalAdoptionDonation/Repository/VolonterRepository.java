package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolonterRepository extends JpaRepository<Volonter, Integer> {
    Optional<Volonter> findByKorisnikId(int korisnikId);
    boolean existsByKorisnikId(int korisnikId);
}
