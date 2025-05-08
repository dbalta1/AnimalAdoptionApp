package ba.unsa.etf.AnimalAdoptionDonation.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class VolonterAkcijaDTOBO {

    private int id;
    private int volonterId;
    private int akcijaId;
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumAkcije;

}
