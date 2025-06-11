package ba.unsa.etf.AnimalAdoptionNotification.dto;

import java.io.Serializable;

public class NotificationFailedEvent implements Serializable {
    private Long userId;
    private String reason;

    public NotificationFailedEvent() {
    }

    public NotificationFailedEvent(Long userId, String reason) {
        this.userId = userId;
        this.reason = reason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
