package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.DonacijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.DonacijaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/donacije")
public class DonacijaController {

    @Autowired
    private DonacijaRepository donacijaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    private final DonacijaService donacijaService;

    public DonacijaController(DonacijaService donacijaService) {
        this.donacijaService = donacijaService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDonacija(@RequestBody DonacijaDTOBO donacijaDTOBO) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(donacijaDTOBO.getKorisnikId());

        if (!korisnik.isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "Korisnik sa ID " + donacijaDTOBO.getKorisnikId() + " ne postoji."));
        }

        Donacija donacija = new Donacija();
        donacija.setVrstaDonacije(donacijaDTOBO.getVrstaDonacije());
        donacija.setIznos(donacijaDTOBO.getIznos());
        donacija.setOpisDonacije(donacijaDTOBO.getOpisDonacije());
        donacija.setDatumDonacije(donacijaDTOBO.getDatumDonacije());
        donacija.setKorisnik(korisnik.get());

        donacijaRepository.save(donacija);

        return ResponseEntity.status(201).body(Map.of("message", "Donacija uspjesno kreirana."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDonacijaById(@PathVariable int id) {
        Optional<DonacijaDTOBO> donacija = donacijaService.getDonacijaById(id);
        return donacija.map(d -> ResponseEntity.ok(Map.<String, Object>of("donacija", d)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Donacija sa ID " + id + " ne postoji.")));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSveDonacije() {
        List<DonacijaDTOBO> donacije = donacijaService.getSveDonacije();
        return ResponseEntity.ok(Map.of("donacije", donacije));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDonacija(@PathVariable int id, @RequestBody DonacijaDTOBO donacijaDTO) {
        // Provjera da li donacija postoji
        Optional<Donacija> existingDonacija = donacijaRepository.findById(id);

        if (existingDonacija.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Donacija sa ID " + id + " ne postoji."));
        }

        Donacija donacija = existingDonacija.get();

        // Provjera da li korisnik postoji
        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(donacijaDTO.getKorisnikId());
        if (korisnikOptional.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "Korisnik sa ID " + donacijaDTO.getKorisnikId() + " ne postoji."));
        }

        // Ažuriranje donacije
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
        donacija.setKorisnik(korisnikOptional.get());  // Ažuriranje korisnika povezanog s donacijom

        donacijaRepository.save(donacija);

        return ResponseEntity.ok(Map.of("message", "Donacija uspjesno azurirana."));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDonacija(@PathVariable int id) {
        boolean deleted = donacijaService.deleteDonacija(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Donacija uspjesno obrisana."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Donacija sa ID " + id + " ne postoji."));
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> createBatchDonacije(
            @Valid @RequestBody List<DonacijaDTOBO> donacijeDTO) {

        try {
            List<DonacijaDTOBO> kreiraneDonacije = donacijaService.createBatchDonacije(donacijeDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Uspjesno kreirano " + kreiraneDonacije.size() + " donacija",
                    "brojKreiranihDonacija", kreiraneDonacije.size(),
                    "donacije", kreiraneDonacije
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
}
