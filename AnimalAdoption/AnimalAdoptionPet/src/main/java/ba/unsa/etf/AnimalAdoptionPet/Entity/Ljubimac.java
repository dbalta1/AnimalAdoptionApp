package ba.unsa.etf.AnimalAdoptionPet.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ljubimac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "sklonisteId", referencedColumnName = "Id", nullable = false)
    private Skloniste skloniste;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String vrsta;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String rasa;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private int godine;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private Spol spol;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private Velicina velicina;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private Energicnost energicnost;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private boolean slaganjeSaDjecom;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private boolean slaganjeSaDrugimLjubimcima;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private boolean zdravstveniProblemi;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String lokacija;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String ime;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String slika;
}
