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
@RequestMapping("/clanci")
public class ClanakController {
    @Autowired
    private ClanakRepository clanakRepository;

    @GetMapping
    public List<ClanakDTO> getAll() {
        return clanakRepository.findAll().stream()
                .map(EntityMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ClanakDTO create(@RequestBody ClanakDTO clanakDTO) {
        Clanak clanak = EntityMapper.INSTANCE.toEntity(clanakDTO);
        return EntityMapper.INSTANCE.toDto(clanakRepository.save(clanak));
    }
}