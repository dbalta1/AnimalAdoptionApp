package ba.unsa.etf.AnimalAdoptionNotification.Listener;

import ba.unsa.etf.AnimalAdoptionNotification.config.RabbitMQConfig;
import ba.unsa.etf.AnimalAdoptionNotification.dto.NotificationFailedEvent;
import ba.unsa.etf.AnimalAdoptionNotification.dto.UserCreatedEvent;
import ba.unsa.etf.AnimalAdoptionNotification.Entity.NotificationsSettings;
import ba.unsa.etf.AnimalAdoptionNotification.Repository.NotificationSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class UserCreatedEventListener {

    private final NotificationSettingsRepository notificationRepo;
    private final RabbitTemplate rabbitTemplate;

    public UserCreatedEventListener(NotificationSettingsRepository notificationRepo, RabbitTemplate rabbitTemplate) {
        this.notificationRepo = notificationRepo;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "user.created")
    public void handleUserCreated(UserCreatedEvent event) {

        try {
            //OVAKO TESTIRATI
            if (event.getEmail().equalsIgnoreCase("test@email.com")) {
                throw new RuntimeException("Simulirana greška u Notification servisu");
            }
            NotificationsSettings settings = new NotificationsSettings(
                    event.getUserId(),
                    event.getEmail(),
                    true
            );
            notificationRepo.save(settings);
            System.out.println("✅ Kreirane notifikacije za korisnika " + event.getEmail());

        } catch (Exception e) {
            System.out.println("❌ Greška pri kreiranju notifikacija: " + e.getMessage());
            NotificationFailedEvent fail = new NotificationFailedEvent(
                    event.getUserId(),
                    "Greška pri upisu u bazu: " + e.getMessage()
            );
            rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_FAILED_QUEUE, fail);
        }
    }
}
