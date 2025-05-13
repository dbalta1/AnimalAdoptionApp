package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
public class Akcija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String nazivAkcije;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumAkcije;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String opisDogadjaja;

    @OneToMany(mappedBy = "volonterskaAkcija")
    private List<VolonterAkcija> volonteri;
}
