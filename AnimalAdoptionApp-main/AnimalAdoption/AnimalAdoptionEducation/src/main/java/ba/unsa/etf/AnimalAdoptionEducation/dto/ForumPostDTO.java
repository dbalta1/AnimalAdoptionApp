package ba.unsa.etf.AnimalAdoptionEducation.dto;

import java.util.Date;
import java.util.UUID;

public class ForumPostDTO {
    private int id;

    @NotNull(message = "ID korisnika je obavezan.")
    private UUID korisnikID;

    @NotNull(message = "Naslov teme je obavezan.")
    @Size(min = 3, max = 255, message = "Naslov mora imati između 3 i 255 karaktera.")
    private String naslovTeme;

    @NotNull(message = "Sadržaj posta je obavezan.")
    private String sadrzajPosta;

    @NotNull(message = "Datum objave je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u budućnosti.")
    private Date datumObjave;
}