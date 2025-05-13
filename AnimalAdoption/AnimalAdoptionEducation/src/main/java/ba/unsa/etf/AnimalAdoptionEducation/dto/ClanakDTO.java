package ba.unsa.etf.AnimalAdoptionEducation.dto;

import java.util.Date;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;



public class ClanakDTO {
    private Long id;

    @NotNull(message = "Naslov je obavezan.")
    @Size(min = 3, max = 255, message = "Naslov mora imati između 3 i 255 karaktera.")
    private String naslov;

    @NotNull(message = "Sadržaj je obavezan.")
    private String sadrzaj;

    @NotNull(message = "Datum kreiranja je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u budućnosti.")
    private Date datumKreiranja;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
}