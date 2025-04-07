package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Donacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "Id", nullable = false)
    private Korisnik korisnik;

    private String vrstaDonacije;
    private double iznos;
    private String opisDonacije;
    private Date datumDonacije;
}
