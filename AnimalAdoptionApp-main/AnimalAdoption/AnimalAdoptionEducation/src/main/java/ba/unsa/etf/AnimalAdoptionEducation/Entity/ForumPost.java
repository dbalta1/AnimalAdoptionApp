package ba.unsa.etf.AnimalAdoptionEducation.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String korisnikID;

    private String naslovTeme;
    private String sadrzajPosta;
    private Date datumObjave;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
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
}
