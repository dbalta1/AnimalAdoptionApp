package ba.unsa.etf.AnimalAdoptionUser.dto;


import ba.unsa.etf.AnimalAdoptionUser.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Uloga;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Data
public class KorisnikDTO {
    private int id;
    private UUID korisnikId;
    private String ime;
    private String prezime;
    private String email;
    private Uloga uloga;
    private String username;
    private String telefon;
    private Spol spol;
    private int godine;
    private String adresa;

}

