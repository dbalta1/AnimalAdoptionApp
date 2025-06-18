package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.VolonterAkcija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolonterAkcijaRepository extends JpaRepository<VolonterAkcija, Integer> {

    boolean existsByVolonterAndVolonterskaAkcija(Volonter volonter, Akcija akcija);
}
