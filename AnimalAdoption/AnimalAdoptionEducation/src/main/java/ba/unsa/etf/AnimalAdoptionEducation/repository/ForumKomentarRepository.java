package ba.unsa.etf.AnimalAdoptionEducation.repository;

import ba.unsa.etf.AnimalAdoptionEducation.Entity.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumKomentarRepository extends JpaRepository<ForumKomentar, Long> {

    //Koristi se za get sve komentare određenog korisnika
    @Query("SELECT k FROM ForumKomentar k WHERE k.korisnikID = :korisnikID")
    List<ForumKomentar> findKomentariByKorisnikId(@Param("korisnikID") String korisnikID);
}