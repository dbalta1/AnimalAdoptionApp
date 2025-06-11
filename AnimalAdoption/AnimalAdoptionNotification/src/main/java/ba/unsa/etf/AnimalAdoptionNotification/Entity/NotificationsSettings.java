package ba.unsa.etf.AnimalAdoptionNotification.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class NotificationsSettings {

    @Id
    private Long userId;

    private String email;

    private boolean enabled = true;

    public NotificationsSettings() {
    }

    public NotificationsSettings(Long userId, String email, boolean enabled) {
        this.userId = userId;
        this.email = email;
        this.enabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
