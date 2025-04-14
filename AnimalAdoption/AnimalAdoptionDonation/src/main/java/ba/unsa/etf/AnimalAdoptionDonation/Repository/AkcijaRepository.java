package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AkcijaRepository extends JpaRepository<Akcija, Integer> {
}
