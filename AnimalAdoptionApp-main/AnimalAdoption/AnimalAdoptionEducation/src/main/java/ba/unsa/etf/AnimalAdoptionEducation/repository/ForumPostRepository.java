package ba.unsa.etf.AnimalAdoptionEducation.repository;

import ba.unsa.etf.AnimalAdoptionEducation.Entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findByNaslovTemeContainingIgnoreCase(String naslovTeme);
}
