package ba.unsa.etf.AnimalAdoptionPet.Configuration;

import ba.unsa.etf.AnimalAdoptionPet.dto.LjubimacCreateDTO;
import ba.unsa.etf.AnimalAdoptionPet.dto.LjubimacDTO;
import ba.unsa.etf.AnimalAdoptionPet.Entity.Ljubimac;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(LjubimacCreateDTO.class, Ljubimac.class).addMappings(mapper ->
                mapper.skip(Ljubimac::setId)
        );

        return modelMapper;
    }
}
