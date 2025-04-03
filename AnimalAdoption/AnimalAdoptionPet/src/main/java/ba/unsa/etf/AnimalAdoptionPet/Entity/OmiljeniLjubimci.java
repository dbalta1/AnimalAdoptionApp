package ba.unsa.etf.AnimalAdoptionPet.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OmiljeniLjubimci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "ljubimacId", referencedColumnName = "Id", nullable = false)
    private Ljubimac ljubimac;

    @Column(nullable = false)
    private UUID korisnikID;

}
