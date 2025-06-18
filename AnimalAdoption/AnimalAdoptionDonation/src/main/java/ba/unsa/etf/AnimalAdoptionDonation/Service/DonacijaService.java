package ba.unsa.etf.AnimalAdoptionDonation.Service;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.DonacijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionDonation.SystemEventsGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonacijaService {

    private final DonacijaRepository donacijaRepository;
    private final KorisnikRepository korisnikRepository;
    private final SystemEventsGrpcClient systemEventsGrpcClient;

    @Autowired
    public DonacijaService(DonacijaRepository donacijaRepository,
                           KorisnikRepository korisnikRepository,
                           SystemEventsGrpcClient systemEventsGrpcClient) {
        this.donacijaRepository = donacijaRepository;
        this.korisnikRepository = korisnikRepository;
        this.systemEventsGrpcClient = systemEventsGrpcClient;
    }

    public boolean createDonacija(Donacija donacija) {
        try {
            Donacija saved = donacijaRepository.save(donacija);
            systemEventsGrpcClient.logEvent("donations", "unknown", "CREATE_DONATION", "donationId:" + saved.getId(), "SUCCESS");
            return true;
        } catch (Exception e) {
            systemEventsGrpcClient.logEvent("donations", "unknown", "CREATE_DONATION", "unknown", "FAILURE");
            return false;
        }
    }

    public Optional<DonacijaDTOBO> getDonacijaById(int id) {
        Optional<Donacija> found = donacijaRepository.findById(id);
        systemEventsGrpcClient.logEvent("donations", "unknown", "GET_DONATION", "donationId:" + id, found.isPresent() ? "SUCCESS" : "FAILURE");
        return found.map(this::mapToDTO);
    }

    public List<DonacijaDTOBO> getSveDonacije() {
        List<DonacijaDTOBO> list = donacijaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
        systemEventsGrpcClient.logEvent("donations", "unknown", "GET_ALL_DONATIONS", "all", "SUCCESS");
        return list;
    }

    public boolean updateDonacija(int id, DonacijaDTOBO donacijaDTO) {
        Optional<Donacija> optionalDonacija = donacijaRepository.findById(id);
        if (optionalDonacija.isEmpty()) {
            systemEventsGrpcClient.logEvent("donations", "unknown", "UPDATE_DONATION", "donationId:" + id, "FAILURE");
            return false;
        }

        Donacija donacija = optionalDonacija.get();

        // Ažurirajte samo postavljena polja
        if (donacijaDTO.getVrstaDonacije() != null) {
            donacija.setVrstaDonacije(donacijaDTO.getVrstaDonacije());
        }
        if (donacijaDTO.getIznos() != 0) {
            donacija.setIznos(donacijaDTO.getIznos());
        }
        if (donacijaDTO.getOpisDonacije() != null) {
            donacija.setOpisDonacije(donacijaDTO.getOpisDonacije());
        }
        if (donacijaDTO.getDatumDonacije() != null) {
            donacija.setDatumDonacije(donacijaDTO.getDatumDonacije());
        }

        // Ažurirajte korisnika ako je potrebno
        if (donacijaDTO.getKorisnikId() != 0) {
            Korisnik korisnik = new Korisnik();
            korisnik.setId(donacijaDTO.getKorisnikId());
            donacija.setKorisnik(korisnik);
        }

        donacijaRepository.save(donacija);
        systemEventsGrpcClient.logEvent("donations", "unknown", "UPDATE_DONATION", "donationId:" + id, "SUCCESS");
        return true;
    }

    public boolean deleteDonacija(int id) {
        if (donacijaRepository.existsById(id)) {
            donacijaRepository.deleteById(id);
            systemEventsGrpcClient.logEvent("donations", "unknown", "DELETE_DONATION", "donationId:" + id, "SUCCESS");
            return true;
        }
        systemEventsGrpcClient.logEvent("donations", "unknown", "DELETE_DONATION", "donationId:" + id, "FAILURE");
        return false;
    }

    public Donacija createDonacija(DonacijaDTOBO donacijaDTOBO) {
        Donacija donacija = new Donacija();
        donacija.setVrstaDonacije(donacijaDTOBO.getVrstaDonacije());
        donacija.setIznos(donacijaDTOBO.getIznos());
        donacija.setOpisDonacije(donacijaDTOBO.getOpisDonacije());
        donacija.setDatumDonacije(donacijaDTOBO.getDatumDonacije());

        // Dodajte korisnika ako je potrebno
        if (donacijaDTOBO.getKorisnikId() != 0) {
            Korisnik korisnik = new Korisnik();
            korisnik.setId(donacijaDTOBO.getKorisnikId());
            donacija.setKorisnik(korisnik);
        }

        Donacija savedDonacija = donacijaRepository.save(donacija);
        systemEventsGrpcClient.logEvent("donations", "unknown", "CREATE_DONATION", "donationId:" + savedDonacija.getId(), "SUCCESS");
        return savedDonacija;
    }

    private DonacijaDTOBO mapToDTO(Donacija donacija) {
        DonacijaDTOBO dto = new DonacijaDTOBO();
        dto.setId(donacija.getId());
        dto.setVrstaDonacije(donacija.getVrstaDonacije());
        dto.setIznos(donacija.getIznos());
        dto.setOpisDonacije(donacija.getOpisDonacije());
        dto.setDatumDonacije(donacija.getDatumDonacije());

        dto.setPaymentUrl(donacija.getPaymentUrl());

        if (donacija.getKorisnik() != null)
            dto.setKorisnikId(donacija.getKorisnik().getId());
        return dto;
    }

    public List<DonacijaDTOBO> createBatchDonacije(List<DonacijaDTOBO> donacijeDTO) {
        if (donacijeDTO == null || donacijeDTO.isEmpty()) {
            systemEventsGrpcClient.logEvent("donations", "unknown", "BATCH_CREATE_DONATION", "none", "FAILURE");
            throw new IllegalArgumentException("Lista donacija ne može biti prazna");
        }

        List<DonacijaDTOBO> rezultat = donacijeDTO.stream()
                .map(this::createSingleDonacija)
                .collect(Collectors.toList());

        systemEventsGrpcClient.logEvent("donations", "unknown", "BATCH_CREATE_DONATION", "count:" + rezultat.size(), "SUCCESS");

        return rezultat;
    }

    private DonacijaDTOBO createSingleDonacija(DonacijaDTOBO donacijaDTO) {
        if (donacijaDTO.getKorisnikId() == 0) {
            throw new IllegalArgumentException("Korisnik ID je obavezan");
        }

        Korisnik korisnik = korisnikRepository.findById(donacijaDTO.getKorisnikId())
                .orElseThrow(() -> new IllegalArgumentException("Korisnik sa ID " + donacijaDTO.getKorisnikId() + " ne postoji"));

        Donacija donacija = new Donacija();
        donacija.setVrstaDonacije(donacijaDTO.getVrstaDonacije());
        donacija.setIznos(donacijaDTO.getIznos());
        donacija.setOpisDonacije(donacijaDTO.getOpisDonacije());
        donacija.setDatumDonacije(donacijaDTO.getDatumDonacije() != null ? donacijaDTO.getDatumDonacije() : LocalDate.now());
        donacija.setKorisnik(korisnik);

        Donacija savedDonacija = donacijaRepository.save(donacija);
        return mapToDTO(savedDonacija);
    }


    /*public void updatePaymentStatus(int donationId, String status) {
        donacijaRepository.findById(donationId).ifPresent(donacija -> {
            donacija.setPaymentStatus(status);
            donacijaRepository.save(donacija);
            systemEventsGrpcClient.logEvent("donations", "unknown",
                    "PAYMENT_" + status.toUpperCase(),
                    "donationId:" + donationId, "SUCCESS");
        });
    }*/
}
