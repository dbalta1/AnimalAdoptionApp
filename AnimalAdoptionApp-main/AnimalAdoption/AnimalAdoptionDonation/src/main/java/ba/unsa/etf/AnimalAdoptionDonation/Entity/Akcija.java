package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

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
    private Date datumAkcije;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String opisDogadjaja;

    @OneToMany(mappedBy = "volonterskaAkcija")
    private Set<VolonterAkcija> volonteri;
}
