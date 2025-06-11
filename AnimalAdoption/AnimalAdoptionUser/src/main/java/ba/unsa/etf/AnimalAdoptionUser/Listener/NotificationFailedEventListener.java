package ba.unsa.etf.AnimalAdoptionUser.listener;

import ba.unsa.etf.AnimalAdoptionUser.Repository.KorisnikRepository;
import ba.unsa.etf.AnimalAdoptionUser.dto.NotificationFailedEvent;
import ba.unsa.etf.AnimalAdoptionUser.Repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationFailedEventListener {

    private final KorisnikRepository userRepository;

    @RabbitListener(queues = "notification.failed")
    public void handleNotificationFailed(NotificationFailedEvent event) {
        try {
            userRepository.deleteById(Math.toIntExact(event.getUserId()));
            System.out.println("❌ Notifikacije nisu kreirane – korisnik obrisan iz baze! ID: " + event.getUserId());
        } catch (Exception e) {
            System.out.println("⚠️ Neuspješan rollback: " + e.getMessage());
        }
    }
}
