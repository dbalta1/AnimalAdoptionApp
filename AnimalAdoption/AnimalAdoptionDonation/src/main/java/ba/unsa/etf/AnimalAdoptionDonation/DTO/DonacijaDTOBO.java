package ba.unsa.etf.AnimalAdoptionDonation.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DonacijaDTOBO {

    private int Id;

    private int korisnikId;


    private String vrstaDonacije;

    @Min(value = 0, message = "Iznos donacije mora biti pozitivan.")
    private double iznos;

    private String opisDonacije;

    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumDonacije;

    /*private String paymentStatus; // "pending", "completed", "failed"
    private String stripePaymentId;*/
    private String paymentUrl;
}
