package ba.unsa.etf.AnimalAdoptionEducation.dto;

import java.util.Date;
import java.util.UUID;

public class ClanakDTO {
    private int id;

    @NotNull(message = "Naslov je obavezan.")
    @Size(min = 3, max = 255, message = "Naslov mora imati između 3 i 255 karaktera.")
    private String naslov;

    @NotNull(message = "Sadržaj je obavezan.")
    private String sadrzaj;

    @NotNull(message = "Datum kreiranja je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u budućnosti.")
    private Date datumKreiranja;
}