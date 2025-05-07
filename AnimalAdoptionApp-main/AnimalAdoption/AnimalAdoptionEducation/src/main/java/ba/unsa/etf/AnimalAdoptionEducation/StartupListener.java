package ba.unsa.etf.AnimalAdoptionEducation;

import jakarta.persistence.EntityManager;
import org.hibernate.stat.Statistics;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Component
public class StartupListener {

    @Autowired
    private EntityManager entityManager;

    @EventListener(ApplicationReadyEvent.class)
    public void logHibernateStats() {
        SessionFactoryImplementor sessionFactory = entityManager.getEntityManagerFactory()
                .unwrap(SessionFactoryImplementor.class);

        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);

        System.out.println(" [HIBERNATE STATS] Fetch Count: " + stats.getEntityFetchCount());
        System.out.println(" [HIBERNATE STATS] Query Execution Count: " + stats.getQueryExecutionCount());
        System.out.println(" [HIBERNATE STATS] Second Level Cache Hits: " + stats.getSecondLevelCacheHitCount());
    }
}





