package ba.unsa.etf.AnimalAdoptionUser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class NotificationFailedEvent {
    private Long userId;
    private String reason;
}
