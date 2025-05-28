package ba.unsa.etf.AnimalAdoptionEducation.dto;

import java.util.Date;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;


public class ForumPostDTO {
    private Long id;

    @NotNull(message = "ID korisnika je obavezan.")
    private String korisnikID;

    @NotNull(message = "Naslov teme je obavezan.")
    @Size(min = 3, max = 255, message = "Naslov mora imati između 3 i 255 karaktera.")
    private String naslovTeme;

    @NotNull(message = "Sadržaj posta je obavezan.")
    private String sadrzajPosta;

    @NotNull(message = "Datum objave je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u budućnosti.")
    private Date datumObjave;

    private String autor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(String korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getNaslovTeme() {
        return naslovTeme;
    }

    public void setNaslovTeme(String naslovTeme) {
        this.naslovTeme = naslovTeme;
    }

    public String getSadrzajPosta() {
        return sadrzajPosta;
    }

    public void setSadrzajPosta(String sadrzajPosta) {
        this.sadrzajPosta = sadrzajPosta;
    }

    public Date getDatumObjave() {
        return datumObjave;
    }

    public void setDatumObjave(Date datumObjave) {
        this.datumObjave = datumObjave;
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
}