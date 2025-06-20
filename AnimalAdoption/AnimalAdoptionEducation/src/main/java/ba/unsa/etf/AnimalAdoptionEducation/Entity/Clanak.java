package ba.unsa.etf.AnimalAdoptionEducation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Clanak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String naslov;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String sadrzaj;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private Date datumKreiranja;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
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
