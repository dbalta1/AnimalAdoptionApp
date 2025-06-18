package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.Client.KorisnikClient;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.DonacijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.DonacijaService;
import ba.unsa.etf.AnimalAdoptionDonation.Service.StripeService;
import ba.unsa.etf.AnimalAdoptionDonation.SystemEventsGrpcClient;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/donacije")
@CrossOrigin(origins = "http://localhost:3000")
public class DonacijaController {

    @Autowired
    private DonacijaRepository donacijaRepository;

    //@Autowired
    //private KorisnikRepository korisnikRepository;

    private final DonacijaService donacijaService;

    @Autowired
    private KorisnikClient korisnikClient;

    /*public DonacijaController(DonacijaService donacijaService) {
        this.donacijaService = donacijaService;
    }*/

   /* @PostMapping
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
        donacija.setKorisnik(korisnik.getId());

        donacijaRepository.save(donacija);

        return ResponseEntity.status(201).body(Map.of("message", "Donacija uspjesno kreirana."));
    }*/

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDonacija(@RequestBody DonacijaDTOBO donacijaDTOBO) {
        try {
            KorisnikDTOBO korisnikDTO = korisnikClient.getKorisnikById((long) donacijaDTOBO.getKorisnikId());

            if (korisnikDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Korisnik nije pronadjen."));
            }

            // Koristite servis za kreiranje donacije
            Donacija donacija = donacijaService.createDonacija(donacijaDTOBO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Donacija uspjesno kreirana."));
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Korisnik nije pronadjen."));
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Greska pri komunikaciji s korisnickim servisom."));
        }
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

    @Autowired
    private KorisnikRepository korisnikRepository;

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDonacija(@PathVariable int id, @RequestBody DonacijaDTOBO donacijaDTO) {
        // Provjera da li korisnik postoji
        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(donacijaDTO.getKorisnikId());
        if (korisnikOptional.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "Korisnik sa ID " + donacijaDTO.getKorisnikId() + " ne postoji."));
        }

        // Koristite servis za ažuriranje donacije
        boolean updated = donacijaService.updateDonacija(id, donacijaDTO);

        if (updated) {
            return ResponseEntity.ok(Map.of("message", "Donacija uspjesno azurirana."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Donacija sa ID " + id + " ne postoji."));
        }
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

    private StripeService stripeService;

    @Autowired
    public DonacijaController(DonacijaService donacijaService, StripeService stripeService) {
        this.donacijaService = donacijaService;
        this.stripeService = stripeService;
    }


    @Autowired
    private SystemEventsGrpcClient systemEventsGrpcClient;

    @PostMapping("/init-stripe")
    public ResponseEntity<Map<String, Object>> initStripePayment(@RequestBody DonacijaDTOBO donacijaDTO) {
        try {
            // 1. Napravi i sačuvaj donaciju u bazi
            Donacija donacija = new Donacija();
            donacija.setIznos(donacijaDTO.getIznos());
            donacija.setVrstaDonacije(donacijaDTO.getVrstaDonacije());
            donacija.setOpisDonacije(donacijaDTO.getOpisDonacije());
            Korisnik korisnik = korisnikRepository.findById(donacijaDTO.getKorisnikId())
                    .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));
            donacija.setKorisnik(korisnik);
            donacija.setDatumDonacije(LocalDate.now());
            //donacija.setPaymentStatus("pending");

            String paymentUrl = stripeService.generatePaymentLink(donacija.getIznos(), donacija.getId());

            donacija.setPaymentUrl(paymentUrl);

            // Napomena: Sačuvaj prvo donaciju da bi dobio ID (ako koristiš auto increment)
            donacija = donacijaRepository.save(donacija);

            // Generiši link opet s validnim ID-om
            paymentUrl = stripeService.generatePaymentLink(donacija.getIznos(), donacija.getId());
            donacija.setPaymentUrl(paymentUrl);
            donacijaRepository.save(donacija); // ažuriraj s ispravnim linkom

            systemEventsGrpcClient.logEvent(
                    "donations",
                    String.valueOf(korisnik.getId()),
                    "CREATE_DONATION",
                    String.valueOf(donacija.getId()),
                    "SUCCESS"
            );



            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("paymentUrl", paymentUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {

            systemEventsGrpcClient.logEvent(
                    "donations",
                    donacijaDTO.getKorisnikId() != 0 ? String.valueOf(donacijaDTO.getKorisnikId()) : "unknown",
                    "CREATE_DONATION",
                    "unknown",
                    "FAILURE"
            );



            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Greška pri kreiranju Stripe uplate: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /*@GetMapping("/confirm")
    public ResponseEntity<String> confirmPayment(
            @RequestParam("donation_id") int donationId) {

        // Ovdje možete dodati dodatnu logiku za verifikaciju
        donacijaService.updatePaymentStatus(donationId, "completed");
        return ResponseEntity.ok("Donacija potvrđena");
    }*/
}
