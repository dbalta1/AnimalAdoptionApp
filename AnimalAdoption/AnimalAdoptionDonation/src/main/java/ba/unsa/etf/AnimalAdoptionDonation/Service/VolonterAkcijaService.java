package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterAkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.VolonterAkcija;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterAkcijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolonterAkcijaService {
    @Autowired
    private VolonterAkcijaRepository volonterAkcijaRepository;

    public List<VolonterAkcijaDTOBO> getAllVolonterAkcije() {
        return volonterAkcijaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<VolonterAkcijaDTOBO> getVolonterAkcijaById(int id) {
        return volonterAkcijaRepository.findById(id).map(this::convertToDTO);
    }

    public VolonterAkcijaDTOBO saveVolonterAkcija(VolonterAkcijaDTOBO volonterAkcijaDTO) {
        VolonterAkcija volonterAkcija = new VolonterAkcija();
        volonterAkcija.setDatumAkcije(volonterAkcijaDTO.getDatumAkcije());
        volonterAkcija = volonterAkcijaRepository.save(volonterAkcija);
        return convertToDTO(volonterAkcija);
    }

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
