package ba.unsa.etf.AnimalAdoptionDonation.Repository;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AkcijaRepository extends JpaRepository<Akcija, Integer> {

    @Query("SELECT a FROM Akcija a WHERE " +
            "(:naziv IS NULL OR LOWER(a.nazivAkcije) LIKE LOWER(CONCAT('%', :naziv, '%'))) AND " +
            "(:datumOd IS NULL OR a.datumAkcije >= :datumOd) AND " +
            "(:datumDo IS NULL OR a.datumAkcije <= :datumDo)")
    Page<Akcija> findByNazivAndDatumBetween(
            @Param("naziv") String naziv,
            @Param("datumOd") LocalDate datumOd,
            @Param("datumDo") LocalDate datumDo,
            Pageable pageable);


}
