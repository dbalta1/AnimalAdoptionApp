package ba.unsa.etf.SystemEvents.Repository;


import ba.unsa.etf.SystemEvents.Entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
