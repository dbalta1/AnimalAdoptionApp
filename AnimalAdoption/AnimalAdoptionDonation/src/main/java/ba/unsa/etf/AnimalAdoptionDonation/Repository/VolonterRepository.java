package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolonterRepository extends JpaRepository<Volonter, Integer> {
}
