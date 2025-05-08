package ba.unsa.etf.AnimalAdoptionDonation.DTO;

import ba.unsa.etf.AnimalAdoptionDonation.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Uloga;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class KorisnikDTOBO {

    private int Id;

    private UUID korisnikId = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private Uloga uloga;

    private String ime;

    private String prezime;

    private String username;

    @Size(min = 8, message = "Password mora sadrzavati minimalno 8 znakova.")
    private String password;

    @Email(message = "E-mail mora biti u ispravnom formatu.")
    private String email;

    @Pattern(regexp = "0?\\d{2}/\\d{3}-\\d{3}", message = "Telefon mora biti u formatu xxx/xxx-xxx.")
    private String telefon;

    @Enumerated(EnumType.STRING)
    private Spol spol;

    private int godine;

    private String adresa;
}
