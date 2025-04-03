package ba.unsa.etf.AnimalAdoptionPet.repository;

import ba.unsa.etf.AnimalAdoptionPet.Entity.Skloniste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SklonisteRepository extends JpaRepository<Skloniste, Integer> {
}
