package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacijaRepository extends JpaRepository<Donacija, Integer> {

}
