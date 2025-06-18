package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterAkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.VolonterAkcija;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterAkcijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.AkcijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolonterAkcijaService {
    @Autowired
    private VolonterAkcijaRepository volonterAkcijaRepository;

    @Autowired
    private VolonterRepository volonterRepository;
    @Autowired
    private AkcijaRepository akcijaRepository;

    public VolonterAkcijaDTOBO saveVolonterAkcija(VolonterAkcijaDTOBO dto) {
        Volonter volonter = volonterRepository.findById(dto.getVolonterId())
                .orElseThrow(() -> new RuntimeException("Volonter nije pronađen"));

        Akcija akcija = akcijaRepository.findById(dto.getAkcijaId())
                .orElseThrow(() -> new RuntimeException("Akcija nije pronađena"));

        // Provjera da li već postoji prijava
        if (volonterAkcijaRepository.existsByVolonterAndVolonterskaAkcija(volonter, akcija)) {
            throw new RuntimeException("Već ste prijavljeni na ovu akciju");
        }

        VolonterAkcija prijava = new VolonterAkcija();
        prijava.setVolonter(volonter);
        prijava.setVolonterskaAkcija(akcija);
        prijava.setDatumAkcije(akcija.getDatumAkcije());

        // Obostrano povezivanje
        volonter.getVolonterskeAkcije().add(prijava);
        akcija.getVolonteri().add(prijava);

        volonterAkcijaRepository.save(prijava);
        return convertToDTO(prijava);
    }








    public List<VolonterAkcijaDTOBO> getAllVolonterAkcije() {
        return volonterAkcijaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<VolonterAkcijaDTOBO> getVolonterAkcijaById(int id) {
        return volonterAkcijaRepository.findById(id).map(this::convertToDTO);
    }

    /*public VolonterAkcijaDTOBO saveVolonterAkcija(VolonterAkcijaDTOBO volonterAkcijaDTO) {
        VolonterAkcija volonterAkcija = new VolonterAkcija();
        volonterAkcija.setDatumAkcije(volonterAkcijaDTO.getDatumAkcije());
        volonterAkcija = volonterAkcijaRepository.save(volonterAkcija);
        return convertToDTO(volonterAkcija);
    }*/

    public void deleteVolonterAkcija(int id) {
        volonterAkcijaRepository.deleteById(id);
    }

    private VolonterAkcijaDTOBO convertToDTO(VolonterAkcija volonterAkcija) {
        VolonterAkcijaDTOBO dto = new VolonterAkcijaDTOBO();
        dto.setId(volonterAkcija.getId());
        dto.setAkcijaId(volonterAkcija.getVolonterskaAkcija().getId());
        dto.setDatumAkcije(volonterAkcija.getDatumAkcije());
        return dto;
    }
}
