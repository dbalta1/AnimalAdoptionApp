package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.DonacijaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonacijaService {

    private final DonacijaRepository donacijaRepository;

    public DonacijaService(DonacijaRepository donacijaRepository) {
        this.donacijaRepository = donacijaRepository;
    }
    public boolean createDonacija(Donacija donacija) {
        try {
            donacijaRepository.save(donacija);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<DonacijaDTOBO> getDonacijaById(int id) {
        return donacijaRepository.findById(id).map(this::mapToDTO);
    }

    public List<DonacijaDTOBO> getSveDonacije() {
        return donacijaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public boolean updateDonacija(int id, DonacijaDTOBO donacijaDTO) {
        Optional<Donacija> optionalDonacija = donacijaRepository.findById(id);
        if (optionalDonacija.isPresent()) {
            Donacija donacija = optionalDonacija.get();
            donacija.setVrstaDonacije(donacijaDTO.getVrstaDonacije());
            donacija.setIznos(donacijaDTO.getIznos());
            donacija.setOpisDonacije(donacijaDTO.getOpisDonacije());
            donacija.setDatumDonacije(donacijaDTO.getDatumDonacije());
            donacijaRepository.save(donacija);
            return true;
        }
        return false;
    }

    public boolean deleteDonacija(int id) {
        if (donacijaRepository.existsById(id)) {
            donacijaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Donacija createDonacija(DonacijaDTOBO donacijaDTOBO) {
        Donacija donacija = new Donacija();
        donacija.setVrstaDonacije(donacijaDTOBO.getVrstaDonacije());
        donacija.setIznos(donacijaDTOBO.getIznos());
        donacija.setOpisDonacije(donacijaDTOBO.getOpisDonacije());
        donacija.setDatumDonacije(donacijaDTOBO.getDatumDonacije());

        return donacijaRepository.save(donacija);
    }

    private DonacijaDTOBO mapToDTO(Donacija donacija) {
        DonacijaDTOBO dto = new DonacijaDTOBO();
        dto.setId(donacija.getId());
        dto.setVrstaDonacije(donacija.getVrstaDonacije());
        dto.setIznos(donacija.getIznos());
        dto.setOpisDonacije(donacija.getOpisDonacije());
        dto.setDatumDonacije(donacija.getDatumDonacije());
        dto.setKorisnikId(donacija.getKorisnik().getId());
        return dto;
    }
}
