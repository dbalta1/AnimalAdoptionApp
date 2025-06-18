package ba.unsa.etf.AnimalAdoptionPet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ZahtjevZaUdomljavanjeDTO {
        private int ljubimacId;
        private UUID korisnikId;
        private String dodatnaNapomena;
        private Date datumOdobrenjaZahtjeva;
        private Date datumPodnosenjaZahtjeva;
}
