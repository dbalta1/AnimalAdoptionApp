package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.dto.*;
import ba.unsa.etf.AnimalAdoptionEducation.entity.*;
import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapper;
import ba.unsa.etf.AnimalAdoptionEducation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum-komentari")
public class ForumKomentarController {
    @Autowired
    private ForumKomentarRepository forumKomentarRepository;

    @GetMapping
    public List<ForumKomentarDTO> getAll() {
        return forumKomentarRepository.findAll().stream()
                .map(EntityMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ForumKomentarDTO create(@RequestBody ForumKomentarDTO forumKomentarDTO) {
        ForumKomentar forumKomentar = EntityMapper.INSTANCE.toEntity(forumKomentarDTO);
        return EntityMapper.INSTANCE.toDto(forumKomentarRepository.save(forumKomentar));
    }
}