package ba.unsa.etf.AnimalAdoptionEducation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.Uloga;
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
    private String ime;
    private String prezime;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}

