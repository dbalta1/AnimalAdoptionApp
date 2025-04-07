package ba.unsa.etf.AnimalAdoptionEducation.dto;

import java.util.Date;
import java.util.UUID;
public class ForumKomentarDTO {
    private int id;

    @NotNull(message = "ID posta je obavezan.")
    private int forumPostId;

    @NotNull(message = "ID korisnika je obavezan.")
    private UUID korisnikID;

    @NotNull(message = "Sadržaj komentara je obavezan.")
    @Size(min = 1, max = 500, message = "Komentar može imati do 500 karaktera.")
    private String sadrzajKomentara;

    @NotNull(message = "Datum komentiranja je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u budućnosti.")
    private Date datumKomentiranja;
}


