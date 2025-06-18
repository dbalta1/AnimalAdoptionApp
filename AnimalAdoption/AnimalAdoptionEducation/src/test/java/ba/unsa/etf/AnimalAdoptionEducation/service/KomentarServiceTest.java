package ba.unsa.etf.AnimalAdoptionEducation.service;

import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumKomentar;
import ba.unsa.etf.AnimalAdoptionEducation.client.KorisnikClient;
import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumKomentarDTO;
import ba.unsa.etf.AnimalAdoptionEducation.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumKomentarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KomentarServiceTest {

    @Mock
    private ForumKomentarRepository komentarRepository;

    @Mock
    private KorisnikClient korisnikClient;

    @InjectMocks
    private KomentarService komentarService;

    public KomentarServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetKomentarSaAutorom() {
        // priprema testnih podataka
        ForumKomentar komentar = new ForumKomentar();
        komentar.setId(1L);
        komentar.setKorisnikID("3");
        komentar.setSadrzajKomentara("Test komentar");
        komentar.setDatumKomentiranja(new Date());

        KorisnikDTO korisnik = new KorisnikDTO();
        korisnik.setId(3);
        korisnik.setIme("Test");
        korisnik.setPrezime("Korisnik");

        when(komentarRepository.findById(1L)).thenReturn(Optional.of(komentar));
        when(korisnikClient.getUserById(3)).thenReturn(korisnik);

        ForumKomentarDTO dto = komentarService.getKomentarSaAutorom(1L);

        assertEquals("Test Korisnik", dto.getAutor());
        assertEquals("Test komentar", dto.getSadrzajKomentara());

        verify(komentarRepository, times(1)).findById(1L);
        verify(korisnikClient, times(1)).getUserById(3);
    }
}
