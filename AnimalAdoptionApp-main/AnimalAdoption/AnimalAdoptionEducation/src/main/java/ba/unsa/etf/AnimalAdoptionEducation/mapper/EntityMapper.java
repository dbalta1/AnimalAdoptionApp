package ba.unsa.etf.AnimalAdoptionEducation.mapper;

import ba.unsa.etf.AnimalAdoptionEducation.dto.*;
import ba.unsa.etf.AnimalAdoptionEducation.Entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    ClanakDTO toDto(Clanak clanak);
    Clanak toEntity(ClanakDTO clanakDTO);

    @Mapping(source = "forumPost.id", target = "forumPostId")
    ForumKomentarDTO toDto(ForumKomentar forumKomentar);

    @Mapping(source = "forumPostId", target = "forumPost", qualifiedByName = "mapToForumPost")
    ForumKomentar toEntity(ForumKomentarDTO forumKomentarDTO);

    ForumPostDTO toDto(ForumPost forumPost);
    ForumPost toEntity(ForumPostDTO forumPostDTO);

    @Named("mapToForumPost")
    static ForumPost mapToForumPost(Long id) {
        if (id == null) return null;
        ForumPost post = new ForumPost();
        post.setId(id);
        return post;
    }
}
