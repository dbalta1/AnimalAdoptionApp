package ba.unsa.etf.AnimalAdoptionPet.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
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
