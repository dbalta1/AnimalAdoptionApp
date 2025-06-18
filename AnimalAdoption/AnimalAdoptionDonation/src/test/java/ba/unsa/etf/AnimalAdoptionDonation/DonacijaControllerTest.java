/*package ba.unsa.etf.AnimalAdoptionDonation;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.DonacijaDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Donacija;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.DonacijaRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.DonacijaService;
import ba.unsa.etf.AnimalAdoptionDonation.Controller.DonacijaController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DonacijaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DonacijaRepository donacijaRepository;

    @Mock
    private KorisnikRepository korisnikRepository;

    @MockBean
    private DonacijaService donacijaService;

    @InjectMocks
    private DonacijaController donacijaController;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void getSveDonacije_ReturnsListOfDonacije() {
        // Priprema mock podataka
        DonacijaDTOBO donacija1 = new DonacijaDTOBO();
        donacija1.setId(1);
        donacija1.setVrstaDonacije("Novčana");
        donacija1.setIznos(100.0);

        DonacijaDTOBO donacija2 = new DonacijaDTOBO();
        donacija2.setId(2);
        donacija2.setVrstaDonacije("Hrana");
        donacija2.setIznos(0.0);

        List<DonacijaDTOBO> donacije = Arrays.asList(donacija1, donacija2);

        // Mock ponašanja servisa
        when(donacijaService.getSveDonacije()).thenReturn(donacije);

        // Kreiramo kontroler ručno sa mock servisom, da ne koristimo injekciju
        DonacijaController controller = new DonacijaController(donacijaService);

        // Pozivamo metodu
        ResponseEntity<Map<String, Object>> response = controller.getSveDonacije();

        // Validacija rezultata
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("donacije"));

        List<DonacijaDTOBO> responseDonacije = (List<DonacijaDTOBO>) response.getBody().get("donacije");
        assertEquals(2, responseDonacije.size());
        assertEquals("Novčana", responseDonacije.get(0).getVrstaDonacije());
        assertEquals("Hrana", responseDonacije.get(1).getVrstaDonacije());

        // Provjera da li je pozvan servis tačno jednom
        verify(donacijaService, times(1)).getSveDonacije();
    }



    @Test
    public void testGetDonacijaById_Success() throws Exception {
        int donacijaId = 6;

        // Fiksiramo datum
        LocalDate fixedDate = LocalDate.of(2025, 4, 14);

        DonacijaDTOBO donacijaDTOBO = new DonacijaDTOBO();
        donacijaDTOBO.setKorisnikId(3);
        donacijaDTOBO.setVrstaDonacije("novac");
        donacijaDTOBO.setIznos(1000.0);
        donacijaDTOBO.setOpisDonacije("opis doniranih sredstava");
        donacijaDTOBO.setDatumDonacije(fixedDate); // koristiš isti fiksirani datum

        when(donacijaService.getDonacijaById(donacijaId)).thenReturn(Optional.of(donacijaDTOBO));

        mockMvc.perform(get("/donacije/{id}", donacijaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.donacija.korisnikId").value(3))
                .andExpect(jsonPath("$.donacija.vrstaDonacije").value("novac"))
                .andExpect(jsonPath("$.donacija.iznos").value(1000.0))
                .andExpect(jsonPath("$.donacija.opisDonacije").value("opis doniranih sredstava"))
                .andExpect(jsonPath("$.donacija.datumDonacije").value(fixedDate.toString())); // očekuješ isti fiksni datum
    }


    @Test
    public void testGetDonacijaById_NotFound() throws Exception {
        when(donacijaService.getDonacijaById(55)).thenReturn(Optional.empty());

        mockMvc.perform(get("/donacije/55"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Donacija sa ID 55 ne postoji."));
    }

    @Test
    void testCreateDonacija() throws Exception {

        DonacijaDTOBO dto = new DonacijaDTOBO();
        dto.setVrstaDonacije("Hrana");
        dto.setIznos(100.0);
        dto.setOpisDonacije("Donacija hrane za pse");
        dto.setDatumDonacije(LocalDate.of(2025, 12, 14));
        dto.setKorisnikId(3); // mora postojati

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // podrška za LocalDate
        String json = objectMapper.writeValueAsString(dto);

        Korisnik korisnik = new Korisnik();
        korisnik.setId(3);
        when(korisnikRepository.findById(3)).thenReturn(Optional.of(korisnik));

        mockMvc.perform(post("/donacije")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Donacija uspjesno kreirana."));
    }

    @Test
    void updateDonacija_SuccessfulUpdate_ReturnsOkResponse() { //1
        // Arrange
        int donacijaId = 1;
        int korisnikId = 1;

        Donacija existingDonacija = new Donacija();
        existingDonacija.setId(donacijaId);
        existingDonacija.setVrstaDonacije("Novac");
        existingDonacija.setIznos(100.0);
        existingDonacija.setOpisDonacije("Početna donacija");
        existingDonacija.setDatumDonacije(LocalDate.of(2023, 1, 1));

        Korisnik korisnik = new Korisnik();
        korisnik.setId(korisnikId);
        existingDonacija.setKorisnik(korisnik);

        DonacijaDTOBO updateDTO = new DonacijaDTOBO();
        updateDTO.setVrstaDonacije("Hrana");
        updateDTO.setIznos(150.0);
        updateDTO.setOpisDonacije("Ažurirana donacija");
        updateDTO.setDatumDonacije(LocalDate.of(2023, 2, 1));
        updateDTO.setKorisnikId(korisnikId);

        when(donacijaRepository.findById(donacijaId)).thenReturn(Optional.of(existingDonacija));
        when(korisnikRepository.findById(korisnikId)).thenReturn(Optional.of(korisnik));
        when(donacijaRepository.save(any(Donacija.class))).thenReturn(existingDonacija);

        // Act
        ResponseEntity<Map<String, Object>> response = donacijaController.updateDonacija(donacijaId, updateDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Donacija uspjesno azurirana.", response.getBody().get("message"));
    }



    @Test
    void testUpdateDonacija_NotFound() throws Exception {
        DonacijaDTOBO dto = new DonacijaDTOBO();
        dto.setVrstaDonacije("Hrana");
        dto.setIznos(50.0);
        dto.setOpisDonacije("Granule za pse");
        dto.setDatumDonacije(LocalDate.of(2025, 4, 10));
        dto.setKorisnikId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(dto);

        when(donacijaRepository.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/donacije/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Donacija sa ID 99 ne postoji."));
    }

    @Test
    void testUpdateDonacija_KorisnikNotFound() throws Exception { //2
        Donacija existingDonacija = new Donacija();
        existingDonacija.setId(1);

        DonacijaDTOBO dto = new DonacijaDTOBO();
        dto.setVrstaDonacije("Hrana");
        dto.setIznos(50.0);
        dto.setOpisDonacije("Granule za pse");
        dto.setDatumDonacije(LocalDate.of(2025, 12, 13));
        dto.setKorisnikId(999); // nepostojeći korisnik

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(dto);

        when(donacijaRepository.findById(1)).thenReturn(Optional.of(existingDonacija));
        when(korisnikRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(put("/donacije/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Korisnik sa ID 999 ne postoji."));
    }

    @Test
    void testDeleteDonacija_Success() throws Exception { //3

        Donacija donacija = new Donacija();
        donacija.setId(2);  // Pretpostavimo da je ID 1
        donacija.setVrstaDonacije("Novčana donacija");
        donacija.setIznos(100);
        donacija.setOpisDonacije("Opis donacije");
        donacija.setDatumDonacije(LocalDate.now());

        when(donacijaRepository.findById(1)).thenReturn(Optional.of(donacija));

        mockMvc.perform(delete("/donacije/2"))
                .andExpect(status().isOk())  // Očekujemo status 200
                .andExpect(jsonPath("$.message").value("Donacija uspjesno obrisana."));
    }

    @Test
    void testDeleteDonacija_NotFound() throws Exception {

        when(donacijaRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/donacije/999"))
                .andExpect(status().isNotFound())  // Očekujemo status 404
                .andExpect(jsonPath("$.message").value("Donacija sa ID 999 ne postoji."));
    }

}
*/