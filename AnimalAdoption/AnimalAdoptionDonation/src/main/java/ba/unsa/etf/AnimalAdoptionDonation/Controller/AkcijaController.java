package ba.unsa.etf.AnimalAdoptionDonation.Controller;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.AkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.DTO.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.VolonterAkcija;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.AkcijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.AkcijaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/akcije")
public class AkcijaController {

    @Autowired
    private AkcijaService akcijaService;

    @Autowired
    private AkcijaRepository akcijaRepository;

    @Autowired
    private VolonterRepository volonterRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAkcija(@Valid @RequestBody AkcijaDTOBO akcijaDTO) {
        AkcijaDTOBO akcija = akcijaService.createAkcija(akcijaDTO);
        return ResponseEntity.ok(Map.of("message", "Akcija uspjesno kreirana!", "akcija", akcija));
    }

    @GetMapping
    public List<AkcijaDTOBO> getAllKorisnici() {
        return akcijaService.getAllAkcije();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAkcijaById(@PathVariable int id) {
        Optional<AkcijaDTOBO> akcija = akcijaService.getAkcijaById(id);
        if (akcija.isPresent()) {
            return ResponseEntity.ok(akcija.get());
        }
        return ResponseEntity.status(404).body(Map.of("message", "Akcija sa ID " + id + " ne postoji."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAkcija(@PathVariable int id, @Valid @RequestBody AkcijaDTOBO akcijaDTOBO) {
        Optional<Akcija> existingAkcija = akcijaRepository.findById(id);

        if (existingAkcija.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Akcija sa ID " + id + " ne postoji."));
        }

        Akcija akcija = existingAkcija.get();

        // Ažuriranje polja
        if (akcijaDTOBO.getNazivAkcije() != null) {
            akcija.setNazivAkcije(akcijaDTOBO.getNazivAkcije());
        }
        if (akcijaDTOBO.getDatumAkcije() != null) {
            akcija.setDatumAkcije(akcijaDTOBO.getDatumAkcije());
        }
        if (akcijaDTOBO.getOpisDogadjaja() != null) {
            akcija.setOpisDogadjaja(akcijaDTOBO.getOpisDogadjaja());
        }
        if (akcijaDTOBO.getVolonteriIds() != null) {
            List<Volonter> volonteri = volonterRepository.findAllById(akcijaDTOBO.getVolonteriIds());

            // Pretvaranje liste Volonter u listu VolonterAkcija
            List<VolonterAkcija> volonteriAkcija = new ArrayList<>();
            for (Volonter volonter : volonteri) {
                VolonterAkcija volonterAkcija = new VolonterAkcija();
                volonterAkcija.setVolonter(volonter);
                volonterAkcija.setVolonterskaAkcija(akcija);
                volonteriAkcija.add(volonterAkcija);
            }

            akcija.setVolonteri(volonteriAkcija);
        }

        akcijaRepository.save(akcija);

        return ResponseEntity.ok(Map.of("message", "Akcija uspjesno azurirana!"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAkcija(@PathVariable int id) {
        boolean deleted = akcijaService.deleteAkcija(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Akcija uspjesno obrisana."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Akcija sa ID " + id + " ne postoji."));
        }
    }

    //dobavljanje podataka prema odredjenom kriteriju, npr po datumu od do nekog zadanog datuma
    @GetMapping("/paginated")
    public ResponseEntity<Page<AkcijaDTOBO>> getAllAkcijePaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "datumAkcije") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<AkcijaDTOBO> akcije = akcijaService.getAllAkcijePaginated(
                page, size, sortBy, sortDirection);
        return ResponseEntity.ok(akcije);
    }



    //ili se mogu akcije sortirati po datumu asc ili desc
    @GetMapping("/search")
    public ResponseEntity<Page<AkcijaDTOBO>> searchAkcije(
            @RequestParam(required = false) String naziv,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumOd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumDo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "datumAkcije") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<AkcijaDTOBO> akcije = akcijaService.searchAkcije(
                naziv, datumOd, datumDo, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(akcije);
    }

}
