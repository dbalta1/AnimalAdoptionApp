package ba.unsa.etf.AnimalAdoptionEducation.service;

import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumPost;
import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumPostDTO;
import ba.unsa.etf.AnimalAdoptionEducation.dto.KorisnikDTO;

import ba.unsa.etf.AnimalAdoptionEducation.client.KorisnikClient;
import ba.unsa.etf.AnimalAdoptionEducation.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ForumPostService {

    @Autowired
    private ForumPostRepository ForumPostRepository;


    @Autowired
    private KorisnikClient korisnikClient;

    public ForumPostDTO getForumPostSaAutorom(Long postId) {
        ForumPost post = ForumPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post ne postoji"));

        int korisnikIntId = Integer.parseInt(post.getKorisnikID());
        KorisnikDTO korisnik;
        try {
            korisnik = korisnikClient.getUserById(korisnikIntId);
        } catch (Exception e) {
            throw new RuntimeException("Korisnik za ovaj post nije pronađen.");
        }

        ForumPostDTO dto = new ForumPostDTO();
        dto.setId(post.getId());
        dto.setNaslovTeme(post.getNaslovTeme());
        dto.setSadrzajPosta(post.getSadrzajPosta());
        dto.setDatumObjave(post.getDatumObjave());
        dto.setKorisnikID(post.getKorisnikID());
        dto.setAutor(korisnik.getIme() + " " + korisnik.getPrezime());

        return dto;
    }

}
