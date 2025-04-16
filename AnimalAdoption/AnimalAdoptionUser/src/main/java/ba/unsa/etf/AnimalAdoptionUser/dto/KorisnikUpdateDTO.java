package ba.unsa.etf.AnimalAdoptionUser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KorisnikUpdateDTO {

        private String ime;
        private String prezime;
        private String email;
        private String username;
        private String password;
        private String telefon;
        private String spol;
        private Integer godine;
        private String adresa;
        private String uloga;

}
