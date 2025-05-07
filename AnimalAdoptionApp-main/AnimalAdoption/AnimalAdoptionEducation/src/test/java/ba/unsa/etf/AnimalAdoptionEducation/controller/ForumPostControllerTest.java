package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapperImpl;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumPostRepository;
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

@WebMvcTest(ForumPostController.class)
@Import(EntityMapperImpl.class)
class ForumPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumPostRepository forumPostRepository;

    @Test
    void getAllPostovi_shouldReturn200() throws Exception {
        mockMvc.perform(get("/forum-postovi/all"))
                .andExpect(status().isOk());
    }

    @Test
    void createPost_shouldReturn400_whenInvalid() throws Exception {
        String invalidPost = "{\"naslovTeme\":\"\", \"sadrzajPosta\":\"\", \"datumObjave\":null}";

        mockMvc.perform(post("/forum-postovi/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPost))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_shouldReturn200_whenExists() throws Exception {
        ForumPost post = new ForumPost();
        post.setId(1L);
        post.setNaslovTeme("Test naslov");
        post.setSadrzajPosta("Test sadrzaj");
        post.setKorisnikID("korisnik1");
        post.setDatumObjave(new Date());

        when(forumPostRepository.findById(1L)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/forum-postovi/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn404_whenNotExists() throws Exception {
        when(forumPostRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/forum-postovi/get/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchNaslov_shouldReturn200_whenValid() throws Exception {
        ForumPost post = new ForumPost();
        post.setId(5L);
        post.setNaslovTeme("Stari naslov");
        post.setKorisnikID("korisnik5");
        post.setDatumObjave(new Date());

        when(forumPostRepository.findById(5L)).thenReturn(Optional.of(post));

        mockMvc.perform(patch("/forum-postovi/patch/5?noviNaslov=Novi naslov"))
                .andExpect(status().isOk());
    }

    @Test
    void patchNaslov_shouldReturn404_whenNotFound() throws Exception {
        when(forumPostRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/forum-postovi/patch/999?noviNaslov=Bilo sta"))
                .andExpect(status().isNotFound());
    }

    @Test
    void pretragaPoNaslovu_shouldReturn200() throws Exception {
        when(forumPostRepository.findByNaslovTemeContainingIgnoreCase("tema"))
                .thenReturn(Collections.singletonList(new ForumPost()));

        mockMvc.perform(get("/forum-postovi/pretraga?naslov=tema"))
                .andExpect(status().isOk());
    }

    @Test
    void batchCreate_shouldReturn200_whenValid() throws Exception {
        String validBatch = "[\n" +
                "  {\"korisnikID\":\"korisnik123\", \"naslovTeme\":\"Naslov 1\", \"sadrzajPosta\":\"Tekst 1\", \"datumObjave\":\"2025-04-20\"},\n" +
                "  {\"korisnikID\":\"korisnik456\", \"naslovTeme\":\"Naslov 2\", \"sadrzajPosta\":\"Tekst 2\", \"datumObjave\":\"2025-04-21\"}\n" +
                "]";

        when(forumPostRepository.saveAll(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/forum-postovi/batch-create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validBatch))
                .andExpect(status().isOk());
    }

    @Test
    void updatePost_shouldReturn400_whenInvalid() throws Exception {
        String invalidUpdate = "{\"naslovTeme\":\"\", \"sadrzajPosta\":\"\", \"datumObjave\":null}";

        mockMvc.perform(put("/forum-postovi/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUpdate))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePost_shouldReturn200_whenExists() throws Exception {
        doNothing().when(forumPostRepository).deleteById(1L);

        mockMvc.perform(delete("/forum-postovi/delete/1"))
                .andExpect(status().isOk());
    }
}
