package ba.unsa.etf.AnimalAdoptionEducation.mapper;

import ba.unsa.etf.AnimalAdoptionEducation.dto.*;
import ba.unsa.etf.AnimalAdoptionEducation.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    ClanakDTO toDto(Clanak clanak);
    Clanak toEntity(ClanakDTO clanakDTO);

    @Mapping(source = "forumPost.id", target = "forumPostId")
    ForumKomentarDTO toDto(ForumKomentar forumKomentar);
    ForumKomentar toEntity(ForumKomentarDTO forumKomentarDTO);

    ForumPostDTO toDto(ForumPost forumPost);
    ForumPost toEntity(ForumPostDTO forumPostDTO);
}