package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class Volonter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @OneToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "Id", nullable = false)
    private Korisnik korisnik;

    @OneToMany(mappedBy = "volonter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VolonterAkcija> volonterskeAkcije = new HashSet<>();

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumVolontiranja;

}
