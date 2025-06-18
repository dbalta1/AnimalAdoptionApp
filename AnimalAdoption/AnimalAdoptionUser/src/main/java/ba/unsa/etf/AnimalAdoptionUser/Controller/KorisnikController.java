package ba.unsa.etf.AnimalAdoptionUser.Controller;

import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionUser.Service.KorisnikService;
import ba.unsa.etf.AnimalAdoptionUser.security.JwtTokenProvider;
import ba.unsa.etf.AnimalAdoptionUser.security.JwtResponse;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Korisnik korisnik) {
        try {
            // Logovanje korisničkog unosa
            logger.info("Login pokušaj za korisnika: {}", korisnik.getEmail());

            // Pretraga korisnika u bazi po emailu
            Korisnik existingUser = korisnikService.getUserByEmail(korisnik.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisnik sa tim email-om ne postoji"));

            // Logovanje šifrovane lozinke
            logger.info("Šifrovana lozinka korisnika: {}", existingUser.getPassword());

            // Upoređivanje unete lozinke sa šifrovanom lozinkom u bazi
            if (!passwordEncoder.matches(korisnik.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.status(401).body("Pogrešna lozinka");
            }

            // Generisanje JWT tokena
            String token = jwtTokenProvider.generateToken(existingUser.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (Exception e) {
            // U slučaju greške
            return ResponseEntity.status(401).body("Pogrešan email ili lozinka");
        }
    }







    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Korisnik korisnik) {
        try {
            korisnikService.createUser(korisnik);
            return ResponseEntity.status(201).body(korisnik);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Greška pri registraciji");
        }
    }
}