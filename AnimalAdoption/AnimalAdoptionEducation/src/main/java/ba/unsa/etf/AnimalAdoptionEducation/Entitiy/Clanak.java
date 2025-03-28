package ba.unsa.etf.AnimalAdoptionEducation.Entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
public class Clanak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String naslov;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String sadrzaj;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private Date datumKreiranja;

}
