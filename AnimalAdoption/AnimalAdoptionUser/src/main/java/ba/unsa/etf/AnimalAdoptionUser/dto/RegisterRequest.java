package ba.unsa.etf.AnimalAdoptionUser.dto;


import ba.unsa.etf.AnimalAdoptionUser.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Uloga;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotNull
    private String ime;
    @NotNull
    private String prezime;
    @NotNull
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "\\d{3}/\\d{3}-\\d{3}")
    private String telefon;
    @NotNull
    private Spol spol;
    private int godine;
    @NotNull
    private String adresa;
    private Uloga uloga; // npr. KORISNIK
}
