package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.DonacijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // za batch

    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<DonacijaDTOBO> createBatchDonacije(List<DonacijaDTOBO> donacijeDTO) {
        // Validacija ulaznih podataka
        if (donacijeDTO == null || donacijeDTO.isEmpty()) {
            throw new IllegalArgumentException("Lista donacija ne moze biti prazna");
        }

        return donacijeDTO.stream()
                .map(this::createSingleDonacija)
                .collect(Collectors.toList());
    }

    private DonacijaDTOBO createSingleDonacija(DonacijaDTOBO donacijaDTO) {
        // Validacija pojedinačne donacije
        if (donacijaDTO.getKorisnikId() == 0) {
            throw new IllegalArgumentException("Korisnik ID je obavezan");
        }

        Korisnik korisnik = korisnikRepository.findById(donacijaDTO.getKorisnikId())
                .orElseThrow(() -> new IllegalArgumentException("Korisnik sa ID " + donacijaDTO.getKorisnikId() + " ne postoji"));

        Donacija donacija = new Donacija();
        donacija.setVrstaDonacije(donacijaDTO.getVrstaDonacije());
        donacija.setIznos(donacijaDTO.getIznos());
        donacija.setOpisDonacije(donacijaDTO.getOpisDonacije());
        donacija.setDatumDonacije(donacijaDTO.getDatumDonacije() != null
                ? donacijaDTO.getDatumDonacije()
                : LocalDate.now());
        donacija.setKorisnik(korisnik);

        Donacija savedDonacija = donacijaRepository.save(donacija);
        return mapToDTO(savedDonacija);
    }
}
