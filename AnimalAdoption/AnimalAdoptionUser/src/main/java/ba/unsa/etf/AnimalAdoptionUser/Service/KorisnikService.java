package ba.unsa.etf.AnimalAdoptionUser.Service;

import ba.unsa.etf.AnimalAdoptionUser.Config.JwtUtil;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Uloga;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionUser.dto.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ba.unsa.etf.AnimalAdoptionUser.dto.LoginRequest;
import ba.unsa.etf.AnimalAdoptionUser.dto.RegisterRequest;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<KorisnikDTO> getAllUsers() {
        return korisnikRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<KorisnikDTO> getUserById(int id) {
        return korisnikRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<KorisnikDTO> getUserByUuid(UUID korisnikId) {
        return korisnikRepository.findByKorisnikId(korisnikId).map(this::convertToDTO);
    }

    public ResponseEntity<Object> createUser(Korisnik korisnikCreateDTO) {
        if (korisnikCreateDTO.getIme() == null || korisnikCreateDTO.getPrezime() == null) {
            return ResponseEntity.badRequest().body("Ime i Prezime su obavezna polja.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikCreateDTO.getIme());
        korisnik.setPrezime(korisnikCreateDTO.getPrezime());
        korisnik.setEmail(korisnikCreateDTO.getEmail());
        korisnik.setUsername(korisnikCreateDTO.getUsername());
        korisnik.setPassword(korisnikCreateDTO.getPassword());
        korisnik.setTelefon(korisnikCreateDTO.getTelefon());
        korisnik.setSpol(korisnikCreateDTO.getSpol());
        korisnik.setGodine(korisnikCreateDTO.getGodine());
        korisnik.setAdresa(korisnikCreateDTO.getAdresa());
        korisnik.setUloga(korisnikCreateDTO.getUloga());

        korisnik.setKorisnikId(UUID.randomUUID());

        Korisnik savedUser = korisnikRepository.save(korisnik);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
    }

    public KorisnikDTO updateUser(int id, Korisnik korisnikUpdateDTO) {
        Korisnik korisnik = korisnikRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Korisnik nije pronađen"));

        if (korisnikUpdateDTO.getIme() != null) korisnik.setIme(korisnikUpdateDTO.getIme());
        if (korisnikUpdateDTO.getPrezime() != null) korisnik.setPrezime(korisnikUpdateDTO.getPrezime());
        if (korisnikUpdateDTO.getEmail() != null) korisnik.setEmail(korisnikUpdateDTO.getEmail());
        if (korisnikUpdateDTO.getUsername() != null) korisnik.setUsername(korisnikUpdateDTO.getUsername());
        if (korisnikUpdateDTO.getPassword() != null) korisnik.setPassword(korisnikUpdateDTO.getPassword());
        if (korisnikUpdateDTO.getTelefon() != null) korisnik.setTelefon(korisnikUpdateDTO.getTelefon());
        if (korisnikUpdateDTO.getSpol() != null) korisnik.setSpol(korisnikUpdateDTO.getSpol());
        korisnik.setGodine(korisnikUpdateDTO.getGodine());
        if (korisnikUpdateDTO.getAdresa() != null) korisnik.setAdresa(korisnikUpdateDTO.getAdresa());
        if (korisnikUpdateDTO.getUloga() != null) korisnik.setUloga(korisnikUpdateDTO.getUloga());

        korisnikRepository.save(korisnik);

        return convertToDTO(korisnik);
    }

    public ResponseEntity<String> deleteUser(int id) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);
        if (korisnik.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID " + id + " nije pronađen.");
        }

        korisnikRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Korisnik sa ID " + id + " je uspešno obrisan.");
    }

    private KorisnikDTO convertToDTO(Korisnik korisnik) {
        KorisnikDTO dto = new KorisnikDTO();
        dto.setId(korisnik.getId());
        dto.setIme(korisnik.getIme());
        dto.setPrezime(korisnik.getPrezime());
        dto.setEmail(korisnik.getEmail());
        dto.setKorisnikId(korisnik.getKorisnikId());
        dto.setUsername(korisnik.getUsername());
        dto.setTelefon(korisnik.getTelefon());
        dto.setSpol(korisnik.getSpol());
        dto.setGodine(korisnik.getGodine());
        dto.setAdresa(korisnik.getAdresa());
        dto.setUloga(korisnik.getUloga());
        return dto;
    }


        public Korisnik patchKorisnik(int id, JsonPatch patch) {
            try {
                Korisnik korisnik = korisnikRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

                ObjectMapper mapper = new ObjectMapper();
                JsonNode korisnikNode = mapper.valueToTree(korisnik);
                JsonNode patched = patch.apply(korisnikNode);

                Korisnik korisnikPatched = mapper.treeToValue(patched, Korisnik.class);
                korisnikPatched.setId(korisnik.getId());

                return korisnikRepository.save(korisnikPatched);
            } catch (Exception e) {
                throw new RuntimeException("Neuspješno patchovanje korisnika", e);
            }
        }
    public List<Korisnik> saveAll(List<Korisnik> korisnici) {
        korisnici.forEach(korisnik -> {
            if (korisnik.getKorisnikId() == null) {
                korisnik.setKorisnikId(UUID.randomUUID());
            }
        });
        return korisnikRepository.saveAll(korisnici);
    }
    public Page<Korisnik> getAllKorisnici(Pageable pageable) {
        return korisnikRepository.findAll(pageable);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(RegisterRequest request) {
        if (korisnikRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(request.getIme());
        korisnik.setPrezime(request.getPrezime());
        korisnik.setUsername(request.getUsername());
        korisnik.setPassword(passwordEncoder.encode(request.getPassword())); // hash lozinke
        korisnik.setEmail(request.getEmail());
        korisnik.setTelefon(request.getTelefon());
        korisnik.setSpol(request.getSpol());
        korisnik.setGodine(request.getGodine());
        korisnik.setAdresa(request.getAdresa());
        korisnik.setUloga(request.getUloga() != null ? request.getUloga() : Uloga.KORISNIK);
        korisnik.setKorisnikId(UUID.randomUUID());

        korisnikRepository.save(korisnik);
        return ResponseEntity.ok("Uspješna registracija.");
    }

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Optional<Korisnik> korisnikOpt = korisnikRepository.findByEmail(request.getEmail());
        if (korisnikOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        Korisnik korisnik = korisnikOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), korisnik.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        String jwt = jwtUtil.generateToken(korisnik.getEmail(), korisnik.getKorisnikId());

        LoginResponse response = new LoginResponse(jwt, korisnik.getUloga().toString());
        return ResponseEntity.ok(response);
    }






}
