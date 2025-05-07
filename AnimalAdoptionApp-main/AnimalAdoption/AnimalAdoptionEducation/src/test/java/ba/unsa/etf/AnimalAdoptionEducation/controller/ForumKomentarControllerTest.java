package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapperImpl;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumKomentarRepository;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumPostRepository;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumKomentar;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ForumKomentarController.class)
@Import(EntityMapperImpl.class)
class ForumKomentarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumKomentarRepository forumKomentarRepository;

    @MockBean
    private ForumPostRepository forumPostRepository;

    @Test
    void getAllKomentari_shouldReturn200() throws Exception {
        mockMvc.perform(get("/forum-komentari/all"))
                .andExpect(status().isOk());
    }

    @Test
    void createKomentar_shouldReturn400_whenInvalid() throws Exception {
        String invalidKomentar = "{\"sadrzajKomentara\":\"\", \"datumKomentiranja\":null}";

        mockMvc.perform(post("/forum-komentari/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidKomentar))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_shouldReturn200_whenExists() throws Exception {
        ForumKomentar komentar = new ForumKomentar();
        komentar.setId(1L);
        komentar.setSadrzajKomentara("Komentar");
        komentar.setDatumKomentiranja(new Date());
        komentar.setKorisnikID("korisnikX");
        komentar.setForumPost(new ForumPost());

        when(forumKomentarRepository.findById(1L)).thenReturn(Optional.of(komentar));

        mockMvc.perform(get("/forum-komentari/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn404_whenNotExists() throws Exception {
        when(forumKomentarRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/forum-komentari/get/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteKomentar_shouldReturn200_whenExists() throws Exception {
        doNothing().when(forumKomentarRepository).deleteById(1L);

        mockMvc.perform(delete("/forum-komentari/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateKomentar_shouldReturn400_whenInvalid() throws Exception {
        String invalidBody = "{\"sadrzajKomentara\":\"\", \"datumKomentiranja\":null}";

        mockMvc.perform(put("/forum-komentari/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest());
    }
}
