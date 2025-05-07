package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapperImpl;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ClanakRepository;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.Clanak;
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

@WebMvcTest(ClanakController.class)
@Import(EntityMapperImpl.class)
public class ClanakControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClanakRepository clanakRepository;

    @Test
    public void shouldReturnAllClanci() throws Exception {
        mockMvc.perform(get("/clanci/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestOnInvalidCreate() throws Exception {
        String invalidBody = "{\"naslov\":\"\", \"sadrzaj\":\"tekst\"}";

        mockMvc.perform(post("/clanci/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn200OnGetById() throws Exception {
        Clanak clanak = new Clanak();
        clanak.setId(1L);
        clanak.setNaslov("Naslov");
        clanak.setSadrzaj("Sadrzaj");
        clanak.setDatumKreiranja(new Date());

        when(clanakRepository.findById(1L)).thenReturn(Optional.of(clanak));

        mockMvc.perform(get("/clanci/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn404OnGetByIdWhenNotFound() throws Exception {
        when(clanakRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clanci/get/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200OnDelete() throws Exception {
        doNothing().when(clanakRepository).deleteById(1L);

        mockMvc.perform(delete("/clanci/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400OnInvalidUpdate() throws Exception {
        String invalidUpdate = "{\"naslov\":\"\", \"sadrzaj\":\"\", \"datumKreiranja\":null}";

        mockMvc.perform(put("/clanci/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUpdate))
                .andExpect(status().isBadRequest());
    }
}
