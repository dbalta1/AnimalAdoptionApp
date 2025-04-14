package ba.unsa.etf.AnimalAdoptionDonation.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class KorisnikDTO {
    private int Id;
    private UUID korisnikId;

    @NotEmpty(message = "Ime je obavezno.")
    private String ime;

    @NotEmpty(message = "Prezime je obavezno.")
    private String prezime;

    @NotEmpty(message = "E-mail je obavezan.")
    @Email(message = "E-mail mora biti u ispravnom formatu.")
    private String email;
}
