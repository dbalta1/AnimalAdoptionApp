package ba.unsa.etf.AnimalAdoptionUser.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "korisnikID", updatable = false, nullable = false)
    private UUID korisnikId;

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
    @Pattern(regexp = "\\d{3}/\\d{3}-\\d{3}", message = "Telefon mora biti u formatu xxx/xxx-xxx.")
    private String telefon;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Spol spol;

    @Column(nullable = false)
    private int godine;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String adresa;

}
