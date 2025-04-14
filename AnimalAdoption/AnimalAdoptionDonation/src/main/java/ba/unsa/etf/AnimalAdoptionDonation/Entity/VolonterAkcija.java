package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
public class VolonterAkcija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "volonterId", referencedColumnName = "Id", nullable = false)
    private Volonter volonter;

    @ManyToOne
    @JoinColumn(name = "akcijaId", referencedColumnName = "Id", nullable = false)
    private Akcija volonterskaAkcija;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumAkcije;
}
