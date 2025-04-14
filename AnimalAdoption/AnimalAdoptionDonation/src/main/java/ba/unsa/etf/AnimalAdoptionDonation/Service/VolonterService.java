package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolonterService {

    private final VolonterRepository volonterRepository;
    private final KorisnikRepository korisnikRepository;

    public VolonterService(VolonterRepository volonterRepository, KorisnikRepository korisnikRepository) {
        this.volonterRepository = volonterRepository;
        this.korisnikRepository = korisnikRepository;
    }
    public boolean createVolonter(Volonter volonter) {
        try {
            volonterRepository.save(volonter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<VolonterDTOBO> getVolonterById(int id) {
        return volonterRepository.findById(id).map(this::mapToDTO);
    }

    public List<VolonterDTOBO> getSveVolontere() {
        return volonterRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Volonter updateVolonter(int id, VolonterDTOBO volonterDTOBO) {
        Volonter volonter = volonterRepository.findById(id).orElse(null);

        if (volonter != null) {
            if (volonterDTOBO.getKorisnikId() != 0) {
                Korisnik korisnik = korisnikRepository.findById(volonterDTOBO.getKorisnikId()).orElse(null);
                if (korisnik != null) {
                    volonter.setKorisnik(korisnik);
                }
            }

            if (volonterDTOBO.getDatumVolontiranja() != null) {
                volonter.setDatumVolontiranja(volonterDTOBO.getDatumVolontiranja());
            }

            if (volonterDTOBO.getVolonterskeAkcijeIds() != null && !volonterDTOBO.getVolonterskeAkcijeIds().isEmpty()) {
                //
            }

            return volonterRepository.save(volonter);
        }

        return null;
    }

    public boolean deleteVolonter(int id) {
        if (volonterRepository.existsById(id)) {
            volonterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Volonter createVolonter(VolonterDTOBO volonterDTOBO) {
        Volonter volonter = new Volonter();
        volonter.setDatumVolontiranja(volonterDTOBO.getDatumVolontiranja());
        volonter.setKorisnik(korisnikRepository.findById(volonterDTOBO.getKorisnikId()).orElse(null));

        return volonterRepository.save(volonter);
    }

    private VolonterDTOBO mapToDTO(Volonter volonter) {
        VolonterDTOBO dto = new VolonterDTOBO();
        dto.setId(volonter.getId());
        dto.setDatumVolontiranja(volonter.getDatumVolontiranja());
        dto.setKorisnikId(volonter.getKorisnik() != null ? volonter.getKorisnik().getId() : null);
        return dto;
    }
}
