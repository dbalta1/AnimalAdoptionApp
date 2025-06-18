package ba.unsa.etf.AnimalAdoptionEducation.service;

import ba.unsa.etf.AnimalAdoptionEducation.client.KorisnikClient;
import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumPostDTO;
import ba.unsa.etf.AnimalAdoptionEducation.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumPost;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumPostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ForumPostServiceTest {

    @Autowired
    private ForumPostService forumPostService;

    @MockBean
    private ForumPostRepository forumPostRepository;

    @MockBean
    private KorisnikClient korisnikClient;

    @Test
    public void testGetForumPostSaAutorom() {
        // Arrange
        ForumPost post = new ForumPost();
        post.setId(1L);
        post.setKorisnikID("2");
        post.setNaslovTeme("Test Naslov");
        post.setSadrzajPosta("Sadržaj posta");
        post.setDatumObjave(new Date());

        when(forumPostRepository.findById(1L)).thenReturn(Optional.of(post));

        KorisnikDTO korisnik = new KorisnikDTO();
        korisnik.setId(2);
        korisnik.setIme("Meho");
        korisnik.setPrezime("Mehić");

        when(korisnikClient.getUserById(2)).thenReturn(korisnik);

        // Act
        ForumPostDTO result = forumPostService.getForumPostSaAutorom(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAutor()).isEqualTo("Meho Mehić");
        assertThat(result.getNaslovTeme()).isEqualTo("Test Naslov");
    }
}
