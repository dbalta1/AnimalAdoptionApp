package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumKomentarDTO;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumKomentar;
import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapper;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumKomentarRepository;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumPostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum-komentari")
public class ForumKomentarController {

    @Autowired
    private ForumKomentarRepository forumKomentarRepository;

    @Autowired
    private ForumPostRepository forumPostRepository;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("/all")
    public List<ForumKomentarDTO> getAll() {
        return forumKomentarRepository.findAll().stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public ForumKomentarDTO getById(@PathVariable Long id) {
        ForumKomentar komentar = forumKomentarRepository.findById(id).orElseThrow();
        return entityMapper.toDto(komentar);
    }

    @PostMapping("/create")
    public ForumKomentarDTO create(@Valid @RequestBody ForumKomentarDTO forumKomentarDTO) {
        ForumKomentar komentar = entityMapper.toEntity(forumKomentarDTO);
        komentar.setForumPost(forumPostRepository.findById(forumKomentarDTO.getForumPostId())
                .orElseThrow(() -> new RuntimeException("Forum post not found")));
        return entityMapper.toDto(forumKomentarRepository.save(komentar));
    }

    @PutMapping("/update/{id}")
    public ForumKomentarDTO update(@PathVariable Long id, @Valid @RequestBody ForumKomentarDTO forumKomentarDTO) {
        ForumKomentar existing = forumKomentarRepository.findById(id).orElseThrow();
        ForumKomentar updated = entityMapper.toEntity(forumKomentarDTO);
        updated.setId(existing.getId());
        updated.setForumPost(forumPostRepository.findById(forumKomentarDTO.getForumPostId())
                .orElseThrow(() -> new RuntimeException("Forum post not found")));
        return entityMapper.toDto(forumKomentarRepository.save(updated));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        forumKomentarRepository.deleteById(id);
    }
}
