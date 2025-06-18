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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumPostService {

    @Autowired
    private ForumPostRepository ForumPostRepository;


    @Autowired
    private KorisnikClient korisnikClient;

    public List<ForumPostDTO> getAllPostoviSaAutorima() {
        List<ForumPost> posts = ForumPostRepository.findAll();

        return posts.stream().map(post -> {
            ForumPostDTO dto = new ForumPostDTO();
            dto.setId(post.getId());
            dto.setNaslovTeme(post.getNaslovTeme());
            dto.setSadrzajPosta(post.getSadrzajPosta());
            dto.setDatumObjave(post.getDatumObjave());
            dto.setKorisnikID(post.getKorisnikID());

            try {
                int korisnikIntId = Integer.parseInt(post.getKorisnikID());
                KorisnikDTO korisnik = korisnikClient.getUserById(korisnikIntId);
                dto.setAutor(korisnik.getIme() + " " + korisnik.getPrezime());
            } catch (Exception e) {
                dto.setAutor("Nepoznat korisnik");
            }

            return dto;
        }).collect(Collectors.toList());
    }

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
