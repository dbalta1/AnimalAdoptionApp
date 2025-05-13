package ba.unsa.etf.AnimalAdoptionPet.service;

import ba.unsa.etf.AnimalAdoptionPet.dto.SklonisteDTO;
import ba.unsa.etf.AnimalAdoptionPet.Entity.Skloniste;
import ba.unsa.etf.AnimalAdoptionPet.repository.SklonisteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SklonisteService {

    private final SklonisteRepository sklonisteRepository;
    private final ModelMapper modelMapper;

    public SklonisteService(SklonisteRepository sklonisteRepository, ModelMapper modelMapper) {
        this.sklonisteRepository = sklonisteRepository;
        this.modelMapper = modelMapper;
    }

    public List<SklonisteDTO> getAllSklonista() {
        List<Skloniste> sklonista = sklonisteRepository.findAll();
        return sklonista.stream()
                .map(skloniste -> modelMapper.map(skloniste, SklonisteDTO.class))
                .collect(Collectors.toList());
    }

    public SklonisteDTO getSklonisteById(int id) {
        Skloniste skloniste = sklonisteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sklonište nije pronađeno."));
        return modelMapper.map(skloniste, SklonisteDTO.class);
    }


    public SklonisteDTO createSkloniste(SklonisteDTO sklonisteDTO) {
        Skloniste skloniste = modelMapper.map(sklonisteDTO, Skloniste.class);
        skloniste = sklonisteRepository.save(skloniste);
        return modelMapper.map(skloniste, SklonisteDTO.class);
    }

    public SklonisteDTO updateSkloniste(int id, SklonisteDTO sklonisteDTO) {
        Skloniste skloniste = sklonisteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sklonište nije pronađeno."));

        // Ažuriranje samo ako su novi podaci dostupni
        if (sklonisteDTO.getIme() != null) skloniste.setIme(sklonisteDTO.getIme());
        if (sklonisteDTO.getAdresa() != null) skloniste.setAdresa(sklonisteDTO.getAdresa());
        if (sklonisteDTO.getBrojTelefona() != null) skloniste.setBrojTelefona(sklonisteDTO.getBrojTelefona());
        if (sklonisteDTO.getEmail() != null) skloniste.setEmail(sklonisteDTO.getEmail());
        if (sklonisteDTO.getGeografskaSirina() != 0) skloniste.setGeografskaSirina(sklonisteDTO.getGeografskaSirina());
        if (sklonisteDTO.getGeografskaDuzina() != 0) skloniste.setGeografskaDuzina(sklonisteDTO.getGeografskaDuzina());

        skloniste = sklonisteRepository.save(skloniste);
        return modelMapper.map(skloniste, SklonisteDTO.class);
    }

    public void deleteSkloniste(int id) {
        if (!sklonisteRepository.existsById(id)) {
            throw new RuntimeException("Sklonište nije pronađeno.");
        }
        sklonisteRepository.deleteById(id);
    }
}
