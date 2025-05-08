package ba.unsa.etf.AnimalAdoptionEducation.dto;

import java.util.Date;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;

public class ForumKomentarDTO {
    private Long id;

    @NotNull(message = "ID posta je obavezan.")
    private Long forumPostId;

    @NotNull(message = "ID korisnika je obavezan.")
    private String korisnikID;

    @NotNull(message = "Sadržaj komentara je obavezan.")
    @Size(min = 1, max = 500, message = "Komentar može imati do 500 karaktera.")
    private String sadrzajKomentara;

    @NotNull(message = "Datum komentiranja je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u budućnosti.")
    private Date datumKomentiranja;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(Long forumPostId) {
        this.forumPostId = forumPostId;
    }

    public String getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(String korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getSadrzajKomentara() {
        return sadrzajKomentara;
    }

    public void setSadrzajKomentara(String sadrzajKomentara) {
        this.sadrzajKomentara = sadrzajKomentara;
    }

    public Date getDatumKomentiranja() {
        return datumKomentiranja;
    }

    public void setDatumKomentiranja(Date datumKomentiranja) {
        this.datumKomentiranja = datumKomentiranja;
    }
}


