package ba.unsa.etf.AnimalAdoptionDonation.DTO;


import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VolonterDTOBO {
    private int id;
    private int korisnikId;
    private List<Integer> volonterskeAkcijeIds;

    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private LocalDate datumVolontiranja;

}
