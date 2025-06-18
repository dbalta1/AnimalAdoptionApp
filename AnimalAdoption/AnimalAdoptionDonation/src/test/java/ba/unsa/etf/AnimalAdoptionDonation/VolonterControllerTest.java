/*package ba.unsa.etf.AnimalAdoptionDonation;

import ba.unsa.etf.AnimalAdoptionDonation.DTO.VolonterDTOBO;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionDonation.Entity.Volonter;
import ba.unsa.etf.AnimalAdoptionDonation.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionDonation.Service.VolonterService;
import ba.unsa.etf.AnimalAdoptionDonation.Controller.VolonterController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class VolonterControllerTest {

    @Mock
    private VolonterService volonterService;

    @Mock
    private KorisnikRepository korisnikRepository;

    @InjectMocks
    private VolonterController volonterController;

    private VolonterDTOBO volonterDTO;
    private Volonter volonter;
    private Korisnik korisnik;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Test Korisnik");

        volonter = new Volonter();
        volonter.setId(1);
        volonter.setDatumVolontiranja(LocalDate.now());
        volonter.setKorisnik(korisnik);

        volonterDTO = new VolonterDTOBO();
        volonterDTO.setId(1);
        volonterDTO.setKorisnikId(1);
        volonterDTO.setDatumVolontiranja(LocalDate.now());
    }

    @Test
    void getAllVolonteri_ReturnsListOfVolonteri() {
        // Arrange
        List<VolonterDTOBO> expectedList = Collections.singletonList(volonterDTO);
        when(volonterService.getSveVolontere()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<VolonterDTOBO>> response = volonterController.getAllVolonteri();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(volonterService, times(1)).getSveVolontere();
    }

    @Test
    void getVolonterById_WhenExists_ReturnsVolonter() {
        // Arrange
        when(volonterService.getVolonterById(1)).thenReturn(Optional.of(volonterDTO));

        // Act
        ResponseEntity<?> response = volonterController.getVolonterById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(volonterDTO, response.getBody());
        verify(volonterService, times(1)).getVolonterById(1);
    }

    @Test
    void getVolonterById_WhenNotExists_ReturnsNotFound() {
        // Arrange
        when(volonterService.getVolonterById(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = volonterController.getVolonterById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Volonter sa ID 1 ne postoji."));
        verify(volonterService, times(1)).getVolonterById(1);
    }

    @Test
    void dodajVolontera_WithValidData_ReturnsCreated() {
        // Arrange
        when(korisnikRepository.findById(1)).thenReturn(Optional.of(korisnik));
        when(volonterService.createVolonter(any(Volonter.class))).thenReturn(true);

        // Act
        ResponseEntity<Map<String, Object>> response = volonterController.dodajVolontera(volonterDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Volonter uspjesno kreiran."));
        verify(korisnikRepository, times(1)).findById(1);
        verify(volonterService, times(1)).createVolonter(any(Volonter.class));
    }

    @Test
    void dodajVolontera_WithInvalidKorisnikId_ReturnsBadRequest() {
        // Arrange
        volonterDTO.setKorisnikId(99);
        when(korisnikRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Map<String, Object>> response = volonterController.dodajVolontera(volonterDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Korisnik sa ID 99 ne postoji."));
        verify(korisnikRepository, times(1)).findById(99);
        verify(volonterService, never()).createVolonter(any(Volonter.class));
    }

    @Test
    void deleteVolonter_WhenExists_ReturnsSuccess() {
        // Arrange
        when(volonterService.deleteVolonter(1)).thenReturn(true);

        // Act
        ResponseEntity<Map<String, Object>> response = volonterController.deleteVolonter(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Volonter uspjesno obrisan!"));
        verify(volonterService, times(1)).deleteVolonter(1);
    }

    @Test
    void deleteVolonter_WhenNotExists_ReturnsNotFound() {
        // Arrange
        when(volonterService.deleteVolonter(1)).thenReturn(false);

        // Act
        ResponseEntity<Map<String, Object>> response = volonterController.deleteVolonter(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Volonter sa ID 1 ne postoji."));
        verify(volonterService, times(1)).deleteVolonter(1);
    }

    @Test
    void updateVolonter_WhenExists_ReturnsSuccess() {
        // Arrange
        when(volonterService.updateVolonter(1, volonterDTO)).thenReturn(volonter);

        // Act
        ResponseEntity<Map<String, String>> response = volonterController.updateVolonter(1, volonterDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Volonter uspjesno azuriran!"));
        verify(volonterService, times(1)).updateVolonter(1, volonterDTO);
    }

    @Test
    void updateVolonter_WhenNotExists_ReturnsNotFound() {
        // Arrange
        when(volonterService.updateVolonter(1, volonterDTO)).thenReturn(null);

        // Act
        ResponseEntity<Map<String, String>> response = volonterController.updateVolonter(1, volonterDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Volonter sa ID 1 nije pronadjen."));
        verify(volonterService, times(1)).updateVolonter(1, volonterDTO);
    }

    @Test
    void updateVolonter_WithInvalidKorisnikId_StillUpdatesOtherFields() {
        // Arrange
        volonterDTO.setKorisnikId(99); // nepostojeći ID
        when(korisnikRepository.findById(99)).thenReturn(Optional.empty());
        when(volonterService.updateVolonter(1, volonterDTO)).thenReturn(volonter);

        // Act
        ResponseEntity<Map<String, String>> response = volonterController.updateVolonter(1, volonterDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(volonterService, times(1)).updateVolonter(1, volonterDTO);
    }

}*/