package ba.unsa.etf.AnimalAdoptionUser.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KorisnikCreateDTO {

    private UUID korisnikId;

    @NotNull(message = "Ime je obavezno.")
    private String ime;

    @NotNull(message = "Prezime je obavezno.")
    private String prezime;

    @NotNull(message = "E-mail je obavezan.")
    @Email(message = "E-mail mora biti u ispravnom formatu.")
    private String email;

    @NotNull(message = "Korisničko ime je obavezno.")
    private String username;

    @NotNull(message = "Lozinka je obavezna.")
    @Size(min = 8, message = "Lozinka mora imati najmanje 8 karaktera.")
    private String password;

    @NotNull(message = "Telefon je obavezan.")
    @Pattern(regexp = "\\d{3}/\\d{3}-\\d{3}", message = "Telefon mora biti u formatu xxx/xxx-xxx.")
    private String telefon;

    @NotNull(message = "Spol je obavezan.")
    private String spol;

    @NotNull(message = "Godine su obavezne.")
    private Integer godine;

    @NotNull(message = "Adresa je obavezna.")
    private String adresa;

    @NotNull(message = "Uloga je obavezna.")
    private String uloga;

    public void setId(int i) {
    }
}

