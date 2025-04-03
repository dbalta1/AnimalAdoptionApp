package ba.unsa.etf.AnimalAdoptionPet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SklonisteDTO {
    private int id;

    private String ime;

    private String adresa;

    @Pattern(regexp = "\\d{3}/\\d{3}-\\d{3}", message = "Telefon mora biti u formatu xxx/xxx-xxx.")
    private String brojTelefona;

    @Email(message = "E-mail mora biti u ispravnom formatu.")
    private String email;

    private double geografskaSirina;

    private double geografskaDuzina;
}
