package ba.unsa.etf.AnimalAdoptionDonation;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.AkcijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Akcija;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.VolonterRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.AkcijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.AkcijaService;
import ba.unsa.etf.AnimalAdoptionDonation.Controller.AkcijaController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;


@SpringBootTest
@AutoConfigureMockMvc
public class AkcijaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AkcijaService akcijaService;

    @InjectMocks
    private AkcijaController akcijaController;

    private List<AkcijaDTOBO> akcije;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VolonterRepository volonterRepository;

    @MockBean
    private AkcijaRepository akcijaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(akcijaController).build();
    }

    @Test
    public void testGetAllAkcije_ShouldReturnAllFields() throws Exception {
        AkcijaDTOBO akcija1 = new AkcijaDTOBO();
        akcija1.setId(1);
        akcija1.setNazivAkcije("Adoptiraj ljubimca");
        akcija1.setDatumAkcije(LocalDate.now().plusDays(10));
        akcija1.setOpisDogadjaja("Akcija udomljavanja pasa i mačaka");
        akcija1.setVolonteriIds(List.of(101, 102));

        AkcijaDTOBO akcija2 = new AkcijaDTOBO();
        akcija2.setId(2);
        akcija2.setNazivAkcije("Doniraj hranu");
        akcija2.setDatumAkcije(LocalDate.now().plusDays(5));
        akcija2.setOpisDogadjaja("Prikupljanje hrane za životinje u skloništu");
        akcija2.setVolonteriIds(List.of(103));

        when(akcijaService.getAllAkcije()).thenReturn(Arrays.asList(akcija1, akcija2));

        mockMvc.perform(get("/akcije"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nazivAkcije", is("Adoptiraj ljubimca")))
                .andExpect(jsonPath("$[0].datumAkcije", is(Arrays.asList(2025, 4, 28))))
                .andExpect(jsonPath("$[0].opisDogadjaja", is("Akcija udomljavanja pasa i mačaka")))
                .andExpect(jsonPath("$[0].volonteriIds[0]", is(101)))
                .andExpect(jsonPath("$[0].volonteriIds[1]", is(102)))


                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nazivAkcije", is("Doniraj hranu")))
                .andExpect(jsonPath("$[0].datumAkcije", is(Arrays.asList(2025, 4, 28))))
                .andExpect(jsonPath("$[1].opisDogadjaja", is("Prikupljanje hrane za životinje u skloništu")))
                .andExpect(jsonPath("$[1].volonteriIds[0]", is(103)));

        verify(akcijaService, times(1)).getAllAkcije();
    }

    @Test
    public void testGetAkcijaById_Success() throws Exception {

        LocalDate testDatum = LocalDate.of(2023, 12, 15);
        AkcijaDTOBO akcija = new AkcijaDTOBO();
        akcija.setId(1);
        akcija.setNazivAkcije("Zimski adoptivni dan");
        akcija.setDatumAkcije(testDatum);
        akcija.setOpisDogadjaja("Posebna akcija udomljavanja zimi");
        akcija.setVolonteriIds(Arrays.asList(101, 102, 103));

        when(akcijaService.getAkcijaById(1)).thenReturn(Optional.of(akcija));

        mockMvc.perform(get("/akcije/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nazivAkcije", is("Zimski adoptivni dan")))
                .andExpect(jsonPath("$.datumAkcije", is(Arrays.asList(2023, 12, 15)))) // Provjera niza
                .andExpect(jsonPath("$.opisDogadjaja", is("Posebna akcija udomljavanja zimi")))
                .andExpect(jsonPath("$.volonteriIds[0]", is(101)))
                .andExpect(jsonPath("$.volonteriIds[1]", is(102)))
                .andExpect(jsonPath("$.volonteriIds[2]", is(103)));

        verify(akcijaService, times(1)).getAkcijaById(1);
    }

    @Test
    public void testGetAkcijaById_NotFound() throws Exception {
        when(akcijaService.getAkcijaById(999)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/akcije/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Akcija sa ID 999 ne postoji."));
    }

    @Test
    void shouldCreateAkcijaSuccessfully() throws Exception {
        AkcijaDTOBO akcijaDTO = new AkcijaDTOBO();
        akcijaDTO.setNazivAkcije("Nova akcija");
        akcijaDTO.setDatumAkcije(LocalDate.now().plusDays(10)); // Budući datum
        akcijaDTO.setOpisDogadjaja("Opis nove akcije");
        akcijaDTO.setVolonteriIds(List.of(1, 2)); // Volonteri ID-ovi

        AkcijaDTOBO kreiranaAkcijaDTO = new AkcijaDTOBO();
        kreiranaAkcijaDTO.setId(1);
        kreiranaAkcijaDTO.setNazivAkcije("Nova akcija");
        kreiranaAkcijaDTO.setDatumAkcije(LocalDate.now().plusDays(10));
        kreiranaAkcijaDTO.setOpisDogadjaja("Opis nove akcije");

        when(akcijaService.createAkcija(any(AkcijaDTOBO.class)))
                .thenReturn(kreiranaAkcijaDTO);

        mockMvc.perform(post("/akcije")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(akcijaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Akcija uspjesno kreirana!"))
                .andExpect(jsonPath("$.akcija.id").value(1))
                .andExpect(jsonPath("$.akcija.nazivAkcije").value("Nova akcija"))
                .andExpect(jsonPath("$.akcija.datumAkcije").exists())
                .andExpect(jsonPath("$.akcija.opisDogadjaja").value("Opis nove akcije"));

        verify(akcijaService, times(1)).createAkcija(any(AkcijaDTOBO.class));
    }

    @Test
    public void testUpdateAkcija_Success() throws Exception {
        int akcijaId = 1;

        Akcija existingAkcija = new Akcija();
        existingAkcija.setId(akcijaId);
        existingAkcija.setNazivAkcije("Stara akcija");

        when(akcijaRepository.findById(akcijaId)).thenReturn(Optional.of(existingAkcija));
        when(akcijaRepository.save(any())).thenReturn(existingAkcija);

        AkcijaDTOBO akcijaDTO = new AkcijaDTOBO();
        akcijaDTO.setNazivAkcije("Nova akcija");
        akcijaDTO.setDatumAkcije(LocalDate.now());
        akcijaDTO.setOpisDogadjaja("Opis nove akcije");

        mockMvc.perform(put("/akcije/{id}", akcijaId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(akcijaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Akcija uspjesno azurirana!"));

        verify(akcijaRepository, times(1)).findById(akcijaId);
        verify(akcijaRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnBadRequestForInvalidDate() throws Exception {
        AkcijaDTOBO akcijaDTO = new AkcijaDTOBO();
        akcijaDTO.setNazivAkcije("Nevalidna Akcija");
        akcijaDTO.setDatumAkcije(LocalDate.now().minusDays(1)); // prošlost

        mockMvc.perform(put("/akcije/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(akcijaDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAkcija_whenExists_thenReturnsOk() throws Exception {
        // 1. Priprema testnih podataka
        int id = 1;
        Akcija akcija = new Akcija();
        akcija.setId(id);

        // 2. Postavi očekivanja
        when(akcijaRepository.findById(id)).thenReturn(Optional.of(akcija));
        when(akcijaService.deleteAkcija(id)).thenReturn(true); // Dodajte ovu liniju

        // 3. Pozovi endpoint i provjeri rezultat
        mockMvc.perform(delete("/akcije/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Akcija uspjesno obrisana."));

        // 4. Verifikacija
        verify(akcijaService, times(1)).deleteAkcija(id);
    }

    @Test
    void deleteAkcija_whenNotFound_thenReturns404() throws Exception {
        int id = 999;

        when(akcijaRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/akcije/999"))
                .andExpect(status().isNotFound())  // Očekujemo status 404
                .andExpect(jsonPath("$.message").value("Akcija sa ID 999 ne postoji."));
    }
}
