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
@RequestMapping("/forum-postovi")
public class ForumPostController {
    @Autowired
    private ForumPostRepository forumPostRepository;

    @GetMapping
    public List<ForumPostDTO> getAll() {
        return forumPostRepository.findAll().stream()
                .map(EntityMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }



    @PostMapping
    public ForumPostDTO create(@RequestBody ForumPostDTO forumPostDTO) {
        ForumPost forumPost = EntityMapper.INSTANCE.toEntity(forumPostDTO);
        return EntityMapper.INSTANCE.toDto(forumPostRepository.save(forumPost));
    }
}