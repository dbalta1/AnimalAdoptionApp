package ba.unsa.etf.AnimalAdoptionEducation.repository;

import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ba.unsa.etf.AnimalAdoptionEducation.repository.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private ClanakRepository clanakRepository;

    @Autowired
    private ForumPostRepository forumPostRepository;

    @Autowired
    private ForumKomentarRepository forumKomentarRepository;

    @Autowired
    private org.hibernate.engine.spi.SessionFactoryImplementor sessionFactory;

    @Test
    public void testNPlusOneProblem() {
        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);

        clanakRepository.findAll();
        assertThat(stats.getPrepareStatementCount()).isLessThan(2);
    }
}