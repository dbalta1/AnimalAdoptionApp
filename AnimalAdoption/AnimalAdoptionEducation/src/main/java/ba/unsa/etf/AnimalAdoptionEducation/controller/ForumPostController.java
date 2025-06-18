package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.dto.ForumPostDTO;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumPost;
import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapper;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ForumPostRepository;
import ba.unsa.etf.AnimalAdoptionEducation.service.ForumPostService;
import ba.unsa.etf.AnimalAdoptionEducation.service.KomentarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum-postovi")
public class ForumPostController {

    @Autowired
    private ForumPostRepository forumPostRepository;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private ForumPostService forumPostService;

    @GetMapping("/all")
    public List<ForumPostDTO> getAll() {
        return forumPostRepository.findAll().stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all-sa-autorom")
    public List<ForumPostDTO> getAllSaAutorom() {
        return forumPostService.getAllPostoviSaAutorima();
    }

    @GetMapping("/get/{id}")
    public ForumPostDTO getById(@PathVariable Long id) {
        ForumPost post = forumPostRepository.findById(id).orElseThrow();
        return entityMapper.toDto(post);
    }

    @GetMapping("/sa-autorom/{id}")
    public ForumPostDTO getForumPostSaAutorom(@PathVariable Long id) {
        return forumPostService.getForumPostSaAutorom(id);
    }


    @PostMapping("/create")
    public ForumPostDTO create(@Valid @RequestBody ForumPostDTO forumPostDTO) {
        ForumPost post = entityMapper.toEntity(forumPostDTO);
        return entityMapper.toDto(forumPostRepository.save(post));
    }

    @PostMapping("/batch-create")
    public List<ForumPostDTO> batchCreate(@Valid @RequestBody List<ForumPostDTO> postDTOs) {
        List<ForumPost> posts = postDTOs.stream()
                .map(entityMapper::toEntity)
                .collect(Collectors.toList());
        return forumPostRepository.saveAll(posts).stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update/{id}")
    public ForumPostDTO update(@PathVariable Long id, @Valid @RequestBody ForumPostDTO forumPostDTO) {
        ForumPost existing = forumPostRepository.findById(id).orElseThrow();
        ForumPost updated = entityMapper.toEntity(forumPostDTO);
        updated.setId(existing.getId());
        return entityMapper.toDto(forumPostRepository.save(updated));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        forumPostRepository.deleteById(id);
    }

    @GetMapping("/pretraga")
    public List<ForumPostDTO> pretraziPoNaslovu(@RequestParam String naslov) {
        return forumPostRepository.findByNaslovTemeContainingIgnoreCase(naslov).stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<ForumPostDTO> patchNaslov(@PathVariable Long id, @RequestParam String noviNaslov) {
        Optional<ForumPost> optionalPost = forumPostRepository.findById(id);
        if (optionalPost.isEmpty()) return ResponseEntity.notFound().build();

        ForumPost post = optionalPost.get();
        post.setNaslovTeme(noviNaslov);
        ForumPost saved = forumPostRepository.save(post);

        return ResponseEntity.ok(entityMapper.toDto(saved));
    }
}
