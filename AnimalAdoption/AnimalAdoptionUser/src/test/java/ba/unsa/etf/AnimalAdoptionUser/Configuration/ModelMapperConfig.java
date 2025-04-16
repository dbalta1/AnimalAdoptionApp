package ba.unsa.etf.AnimalAdoptionUser.Configuration;

import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import ba.unsa.etf.AnimalAdoptionUser.dto.KorisnikCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(KorisnikCreateDTO.class, Korisnik.class).addMappings(mapper ->
                mapper.skip(Korisnik::setId)
        );
        return modelMapper;
    }
}