package ba.unsa.etf.AnimalAdoptionPet.service;

import ba.unsa.etf.AnimalAdoptionPet.Entity.Ljubimac;
import ba.unsa.etf.AnimalAdoptionPet.dto.LjubimacCreateDTO;
import ba.unsa.etf.AnimalAdoptionPet.dto.LjubimacDTO;
import ba.unsa.etf.AnimalAdoptionPet.repository.LjubimacRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LjubimacService {
    private final LjubimacRepository ljubimacRepository;
    private final ModelMapper modelMapper;

    public LjubimacService(LjubimacRepository ljubimacRepository, ModelMapper modelMapper) {
        this.ljubimacRepository = ljubimacRepository;
        this.modelMapper = modelMapper;
    }

    public List<LjubimacDTO> getAllLjubimci() {
        return ljubimacRepository.findAll().stream()
                .map(ljubimac -> modelMapper.map(ljubimac, LjubimacDTO.class))
                .collect(Collectors.toList());
    }

    public LjubimacDTO getLjubimacById(int id) {
        Ljubimac ljubimac = ljubimacRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ljubimac nije pronađen."));
        return modelMapper.map(ljubimac, LjubimacDTO.class);
    }

    @Transactional
    public LjubimacDTO createLjubimac(LjubimacCreateDTO ljubimacCreateDTO) {
        Ljubimac ljubimac = modelMapper.map(ljubimacCreateDTO, Ljubimac.class);
        ljubimac = ljubimacRepository.save(ljubimac);
        return modelMapper.map(ljubimac, LjubimacDTO.class);
    }

    public LjubimacDTO updateLjubimac(int id, LjubimacDTO ljubimacDTO) {
        Ljubimac existingLjubimac = ljubimacRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ljubimac nije pronađen."));

        modelMapper.map(ljubimacDTO, existingLjubimac);
        existingLjubimac.setId(id);
        existingLjubimac = ljubimacRepository.save(existingLjubimac);
        return modelMapper.map(existingLjubimac, LjubimacDTO.class);
    }

    public void deleteLjubimac(int id) {
        if (!ljubimacRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ljubimac nije pronađen.");
        }
        ljubimacRepository.deleteById(id);
    }
}

