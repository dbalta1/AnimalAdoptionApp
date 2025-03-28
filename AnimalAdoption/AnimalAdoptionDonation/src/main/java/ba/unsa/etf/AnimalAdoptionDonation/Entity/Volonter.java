package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Volonter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @OneToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "Id", nullable = false)
    private Korisnik korisnik;

    @OneToMany(mappedBy = "volonter")
    private Set<VolonterAkcija> volonterskeAkcije;

}
