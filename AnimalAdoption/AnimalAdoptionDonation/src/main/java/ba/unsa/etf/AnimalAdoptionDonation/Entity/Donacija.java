package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Donacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "Id", nullable = false)
    private Korisnik korisnik;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String vrstaDonacije;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @Min(value = 0, message = "Iznos donacije mora biti pozitivan.")
    private double iznos;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String opisDonacije;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumDonacije;

    private String paymentUrl;
    /*private String paymentStatus;
    private String stripePaymentId;*/
}
