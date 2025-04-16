package ba.unsa.etf.AnimalAdoptionUser.Controller;

import ba.unsa.etf.AnimalAdoptionUser.AnimalAdoptionUserApplication;
import ba.unsa.etf.AnimalAdoptionUser.Configuration.ModelMapperConfig;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Spol;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Uloga;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionUser.Service.KorisnikService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {AnimalAdoptionUserApplication.class, ModelMapperConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")

class KorisnikControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private KorisnikRepository korisnikRepository;

    int korisnikId;

    @BeforeAll
    void setUp() {
        korisnikRepository.deleteAll();
        Korisnik korisnik1 = new Korisnik();
        korisnik1.setIme("Ana");
        korisnik1.setPrezime("Anić");
        korisnik1.setUsername("ana123");
        korisnik1.setPassword("Password1!");
        korisnik1.setEmail("ana@example.com");
        korisnik1.setTelefon("033/123-456");
        korisnik1.setAdresa("Neka adresa 123");
        korisnik1.setSpol(Spol.ZENSKI);
        korisnik1.setUloga(Uloga.KORISNIK);
        korisnik1.setGodine(28);


        Korisnik korisnik2 = new Korisnik();
        korisnik2.setIme("Marko");
        korisnik2.setPrezime("Marić");
        korisnik2.setUsername("marko123");
        korisnik2.setPassword("Password2!");
        korisnik2.setEmail("marko@example.com");
        korisnik2.setTelefon("061/987-654");
        korisnik2.setAdresa("Druga ulica 456");
        korisnik2.setSpol(Spol.MUSKI);
        korisnik2.setUloga(Uloga.KORISNIK);
        korisnik2.setGodine(32);
        korisnikService.createUser(korisnik1);
        korisnikService.createUser(korisnik2);
    }


    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/korisnici"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getUserById() throws Exception {
        Korisnik korisnik = korisnikRepository.findAll().get(0);
        mockMvc.perform(get("/korisnici/" + korisnik.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(korisnik.getUsername())));
    }
    @Test
    void createUser() throws Exception {
        String noviKorisnikJson = """
        {
            "ime": "Ivana",
            "prezime": "Ivić",
            "username": "ivana123",
            "password": "Password3!",
            "email": "ivana@example.com",
            "telefon": "062/111-222",
            "adresa": "Treća ulica 789",
            "spol": "ZENSKI",
            "uloga": "KORISNIK",
            "godine": 25
        }
        """;

        mockMvc.perform(post("/korisnici")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noviKorisnikJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("ivana123")));
    }
    @Test
    void updateUser() throws Exception {
        Korisnik korisnik = korisnikRepository.findAll().get(0);

        String updateJson = """
        {
            "ime": "Ana",
            "prezime": "Anić",
            "username": "anaUpdated",
            "password": "Password1!",
            "email": "ana@example.com",
            "telefon": "033/123-456",
            "adresa": "Nova adresa 123",
            "spol": "ZENSKI",
            "uloga": "KORISNIK",
            "godine": 29
        }
        """;

        mockMvc.perform(put("/korisnici/" + korisnik.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("anaUpdated")));
    }
}
