package ba.unsa.etf.AnimalAdoptionUser.Controller;

//import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumKomentarDTO;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionUser.Service.KorisnikService;
import ba.unsa.etf.AnimalAdoptionUser.dto.LoginRequest;
import ba.unsa.etf.AnimalAdoptionUser.dto.RegisterRequest;
import com.github.fge.jsonpatch.JsonPatch;
import ba.unsa.etf.AnimalAdoptionUser.Repository.KorisnikRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Value("${server.port}")
    private String port;
    private static final Logger logger = LoggerFactory.getLogger(KorisnikController.class);

    @GetMapping
    public List<KorisnikDTO> getAllUsers() {
        return korisnikService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<KorisnikDTO> getUserById(@PathVariable int id) {
        return korisnikService.getUserById(id);
    }


    @GetMapping("/uuid/{korisnikId}")
    public Optional<KorisnikDTO> getUserByUuid(@PathVariable UUID korisnikId) {
        logger.info("🌀 Pozvana instanca user-service na portu: {}", port);
        return korisnikService.getUserByUuid(korisnikId);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody Korisnik korisnikCreateDTO) {
        return korisnikService.createUser(korisnikCreateDTO);

    }

    @PutMapping("/{id}")
    public KorisnikDTO updateUser(@PathVariable int id, @Valid @RequestBody Korisnik korisnikUpdateDTO) {
        return korisnikService.updateUser(id, korisnikUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return korisnikService.deleteUser(id);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Korisnik> patchKorisnik(@PathVariable int id, @RequestBody JsonPatch patch) {
        try {
            return ResponseEntity.ok(korisnikService.patchKorisnik(id, patch));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/batch")
    public ResponseEntity<List<Korisnik>> batchSave(@RequestBody List<Korisnik> korisnici) {
        return ResponseEntity.ok(korisnikService.saveAll(korisnici));
    }
    @GetMapping("/ge")
    public ResponseEntity<Page<Korisnik>> getKorisnici(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "prezime") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(korisnikService.getAllKorisnici(pageable));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        try {
            return korisnikService.register(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška: " + e.getMessage());
        }
    }


    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return korisnikService.login(request);
    }









}


