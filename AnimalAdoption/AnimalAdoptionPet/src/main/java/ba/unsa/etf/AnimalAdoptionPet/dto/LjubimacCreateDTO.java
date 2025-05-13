package ba.unsa.etf.AnimalAdoptionPet.dto;

import ba.unsa.etf.AnimalAdoptionPet.Entity.Energicnost;
import ba.unsa.etf.AnimalAdoptionPet.Entity.Skloniste;
import ba.unsa.etf.AnimalAdoptionPet.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionPet.Entity.Velicina;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LjubimacCreateDTO {

    private int sklonisteId;

    private String vrsta;

    private String rasa;

    private int godine;

    private Spol spol;

    private Velicina velicina;

    private Energicnost energicnost;

    private boolean slaganjeSaDjecom;

    private boolean slaganjeSaDrugimLjubimcima;

    private boolean zdravstveniProblemi;

    private String lokacija;


    private String ime;

    private String slika;
}



