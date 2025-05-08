package ba.unsa.etf.AnimalAdoptionDonation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "korisnikId", updatable = false, nullable = false, unique = true)
    private UUID korisnikId = UUID.randomUUID();

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Uloga uloga;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String ime;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String prezime;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Size(min = 8, message = "Password mora sadrzavati minimalno 8 znakova.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false, unique = true)
    @Email(message = "E-mail mora biti u ispravnom formatu.")
    private String email;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "0?\\d{2}/\\d{3}-\\d{3}", message = "Telefon mora biti u formatu xxx/xxx-xxx.")
    private String telefon;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Spol spol;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private int godine;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String adresa;

    @OneToOne(mappedBy = "korisnik", cascade=CascadeType.ALL)
    private Volonter volonter;

}
