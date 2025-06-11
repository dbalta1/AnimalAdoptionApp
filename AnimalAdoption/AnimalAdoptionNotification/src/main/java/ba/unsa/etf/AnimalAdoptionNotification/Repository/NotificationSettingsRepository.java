package ba.unsa.etf.AnimalAdoptionNotification.Repository;

import ba.unsa.etf.AnimalAdoptionNotification.Entity.NotificationsSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingsRepository extends JpaRepository<NotificationsSettings, Long> {
}