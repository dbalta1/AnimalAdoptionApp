package ba.unsa.etf.AnimalAdoptionEducation.service;

import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumKomentar;
import ba.unsa.etf.AnimalAdoptionEducation.client.KorisnikClient;
import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumKomentarDTO;
import ba.unsa.etf.AnimalAdoptionEducation.dto.KorisnikDTO;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumKomentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KomentarService {

    @Autowired
    private ForumKomentarRepository komentarRepository;

    @Autowired
    private KorisnikClient korisnikClient;

    public ForumKomentarDTO getKomentarSaAutorom(Long komentarId) {
        ForumKomentar komentar = komentarRepository.findById(komentarId)
                .orElseThrow(() -> new RuntimeException("Komentar ne postoji"));

        int korisnikIntId = Integer.parseInt(komentar.getKorisnikID());
        KorisnikDTO korisnik = korisnikClient.getUserById(korisnikIntId);

        ForumKomentarDTO dto = new ForumKomentarDTO();
        dto.setId(komentar.getId());
        dto.setForumPostId(komentar.getId());
        dto.setKorisnikID(komentar.getKorisnikID());
        dto.setSadrzajKomentara(komentar.getSadrzajKomentara());
        dto.setDatumKomentiranja(komentar.getDatumKomentiranja());
        dto.setAutor(korisnik.getIme() + " " + korisnik.getPrezime());

        return dto;
    }

    public List<ForumKomentarDTO> getKomentariZaKorisnika(int korisnikId) {
        List<ForumKomentar> komentari = komentarRepository.findKomentariByKorisnikId(String.valueOf(korisnikId));

        KorisnikDTO korisnik = null;
        try {
            korisnik = korisnikClient.getUserById(korisnikId);
        } catch (Exception e) {
            throw new RuntimeException("Korisnik sa ID " + korisnikId + " nije pronađen ili servis nije dostupan.");
        }

        if (korisnik == null)
            throw new RuntimeException("Korisnik sa ID " + korisnikId + " ne postoji.");

        String autor = korisnik.getIme() + " " + korisnik.getPrezime();

        return komentari.stream().map(komentar -> {
            ForumKomentarDTO dto = new ForumKomentarDTO();
            dto.setId(komentar.getId());
            dto.setId(komentar.getId()); // ili getForumPost().getId()
            dto.setKorisnikID(komentar.getKorisnikID());
            dto.setSadrzajKomentara(komentar.getSadrzajKomentara());
            dto.setDatumKomentiranja(komentar.getDatumKomentiranja());
            dto.setAutor(autor);
            return dto;
        }).collect(Collectors.toList());
    }
}
