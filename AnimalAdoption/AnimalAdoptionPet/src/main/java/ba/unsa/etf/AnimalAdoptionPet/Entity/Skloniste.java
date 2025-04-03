package ba.unsa.etf.AnimalAdoptionPet.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Skloniste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String ime;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private String adresa;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{3}/\\d{3}-\\d{3}", message = "Telefon mora biti u formatu xxx/xxx-xxx.")
    private String brojTelefona;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false, unique = true)
    @Email(message = "E-mail mora biti u ispravnom formatu.")
    private String email;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private double geografskaSirina;

    @NotNull(message = "Ovaj podatak je obavezan.")
    @Column(nullable = false)
    private double geografskaDuzina;

}
