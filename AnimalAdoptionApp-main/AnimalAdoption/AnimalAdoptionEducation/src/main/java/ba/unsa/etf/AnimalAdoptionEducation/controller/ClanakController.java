package ba.unsa.etf.AnimalAdoptionEducation.controller;

import ba.unsa.etf.AnimalAdoptionEducation.dto.ClanakDTO;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.Clanak;
import ba.unsa.etf.AnimalAdoptionEducation.mapper.EntityMapper;
import ba.unsa.etf.AnimalAdoptionEducation.repository.ClanakRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clanci")
public class ClanakController {

    @Autowired
    private ClanakRepository clanakRepository;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("/all")
    public List<ClanakDTO> getAll() {
        return clanakRepository.findAll().stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/paginated")
    public Page<ClanakDTO> getAllPaginated(Pageable pageable) {
        return clanakRepository.findAll(pageable)
                .map(entityMapper::toDto);
    }

    @GetMapping("/get/{id}")
    public ClanakDTO getById(@PathVariable Long id) {
        Clanak clanak = clanakRepository.findById(id).orElseThrow();
        return entityMapper.toDto(clanak);
    }

    @PostMapping("/create")
    public ClanakDTO create(@Valid @RequestBody ClanakDTO clanakDTO) {
        Clanak clanak = entityMapper.toEntity(clanakDTO);
        return entityMapper.toDto(clanakRepository.save(clanak));
    }

    @PutMapping("/update/{id}")
    public ClanakDTO update(@PathVariable Long id, @Valid @RequestBody ClanakDTO clanakDTO) {
        Clanak existing = clanakRepository.findById(id).orElseThrow();
        Clanak updated = entityMapper.toEntity(clanakDTO);
        updated.setId(existing.getId());
        return entityMapper.toDto(clanakRepository.save(updated));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        clanakRepository.deleteById(id);
    }
}
