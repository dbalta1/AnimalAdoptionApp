package ba.unsa.etf.AnimalAdoptionEducation.Entitiy;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private UUID korisnikID;  // UUID korisnika iz druge baze

    private String naslovTeme;
    private String sadrzajPosta;
    private Date datumObjave;

}
