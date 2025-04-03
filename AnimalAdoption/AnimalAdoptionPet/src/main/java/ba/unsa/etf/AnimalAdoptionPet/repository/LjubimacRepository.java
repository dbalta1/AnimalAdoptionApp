package ba.unsa.etf.AnimalAdoptionPet.repository;

import ba.unsa.etf.AnimalAdoptionPet.Entity.Ljubimac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LjubimacRepository extends JpaRepository<Ljubimac, Integer> {
}