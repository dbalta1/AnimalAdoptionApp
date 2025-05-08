package ba.unsa.etf.AnimalAdoptionDonation.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AkcijaDTOBO {

    private int id;
    private String nazivAkcije;

    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumAkcije;
    private String opisDogadjaja;
    private List<Integer> volonteriIds;

}
