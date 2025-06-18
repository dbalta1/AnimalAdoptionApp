package ba.unsa.etf.AnimalAdoptionPet.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ZahtjevZaUdomljavanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne
    @JoinColumn(name = "ljubimacId", referencedColumnName = "Id", nullable = false)
    private Ljubimac ljubimac;

    @Column(nullable = false)
    private UUID korisnikID;  // UUID korisnika iz druge baze

    @NotNull
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private Date datumPodnosenjaZahtjeva;
    @NotNull
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private Date datumOdobrenjaZahtjeva;

    @NotNull
    @Column(nullable = false)
    private StatusZahtjeva statusZahtjeva;

    private String dodatnaNapomena;
}
