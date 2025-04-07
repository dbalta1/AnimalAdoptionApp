package ba.unsa.etf.AnimalAdoptionEducation.Entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class ForumKomentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "Id", nullable = false)
    private ForumPost forumPost;

    @Column(nullable = false)
    private UUID korisnikID;  // UUID korisnika iz druge baze

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String sadrzajKomentara;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @FutureOrPresent(message = "Datum mora biti danas ili u buducnosti.")
    private Date datumKomentiranja;
}
