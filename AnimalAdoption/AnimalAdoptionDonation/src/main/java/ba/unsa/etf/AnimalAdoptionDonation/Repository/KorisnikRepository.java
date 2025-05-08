package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    Optional<Korisnik> findById(int Id);

}
