package ba.unsa.etf.AnimalAdoptionEducation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
public class ForumKomentar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forum_post_id", referencedColumnName = "id", nullable = false)
    private ForumPost forumPost;

    @Column(nullable = false)
    private String korisnikID;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String sadrzajKomentara;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private Date datumKomentiranja;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ForumPost getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
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
