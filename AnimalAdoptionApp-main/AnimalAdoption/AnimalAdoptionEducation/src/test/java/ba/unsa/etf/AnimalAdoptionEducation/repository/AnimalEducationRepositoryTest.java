package ba.unsa.etf.AnimalAdoptionEducation.repository;

import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ba.unsa.etf.AnimalAdoptionEducation.repository.*;
import jakarta.persistence.EntityManager;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnimalEducationRepositoryTest {

    @Autowired
    private ClanakRepository clanakRepository;

    @Autowired
    private ForumPostRepository forumPostRepository;

    @Autowired
    private ForumKomentarRepository forumKomentarRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testNPlusOneProblem() {
        SessionFactoryImplementor sessionFactory = entityManager
                .getEntityManagerFactory()
                .unwrap(SessionFactoryImplementor.class);

        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);

        clanakRepository.findAll();
        assertThat(stats.getPrepareStatementCount()).isLessThan(2);
    }

}