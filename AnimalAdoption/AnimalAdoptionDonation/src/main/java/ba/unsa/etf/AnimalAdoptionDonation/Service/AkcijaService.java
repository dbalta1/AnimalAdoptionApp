package ba.unsa.etf.AnimalAdoptionDonation.Service;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.*;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.*;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AkcijaService {
    @Autowired
    private AkcijaRepository akcijaRepository;

    public List<AkcijaDTOBO> getAllAkcije() {
        return akcijaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<AkcijaDTOBO> getAkcijaById(int id) {
        return akcijaRepository.findById(id).map(this::convertToDTO);
    }

    public AkcijaDTOBO createAkcija(AkcijaDTOBO akcijaDTOBO) {
        Akcija akcija = new Akcija();
        akcija.setNazivAkcije(akcijaDTOBO.getNazivAkcije());
        akcija.setDatumAkcije(akcijaDTOBO.getDatumAkcije());
        akcija.setOpisDogadjaja(akcijaDTOBO.getOpisDogadjaja());

        Akcija savedAkcija = akcijaRepository.save(akcija);
        return convertToDTO(savedAkcija);
    }

    public boolean deleteAkcija(int id) {
        Optional<Akcija> akcija = akcijaRepository.findById(id);
        if (akcija.isPresent()) {
            akcijaRepository.delete(akcija.get());
            return true;
        }
        return false;
    }

    private AkcijaDTOBO convertToDTO(Akcija akcija) {
        AkcijaDTOBO dto = new AkcijaDTOBO();
        dto.setId(akcija.getId());
        dto.setNazivAkcije(akcija.getNazivAkcije());
        dto.setDatumAkcije(akcija.getDatumAkcije());
        dto.setOpisDogadjaja(akcija.getOpisDogadjaja());
        return dto;
    }

    public Optional<AkcijaDTOBO> updateAkcija(int id, AkcijaDTOBO akcijaDTOBO) {
        return akcijaRepository.findById(id).map(existingAkcija -> {
            Akcija updatedAkcija = convertToEntity(akcijaDTOBO, existingAkcija);
            updatedAkcija = akcijaRepository.save(updatedAkcija);
            return convertToDTO(updatedAkcija);
        });
    }

    private Akcija convertToEntity(AkcijaDTOBO akcijaDTOBO, Akcija existingAkcija) {
        existingAkcija.setNazivAkcije(akcijaDTOBO.getNazivAkcije());
        existingAkcija.setDatumAkcije(akcijaDTOBO.getDatumAkcije());
        existingAkcija.setOpisDogadjaja(akcijaDTOBO.getOpisDogadjaja());
        return existingAkcija;
    }

}