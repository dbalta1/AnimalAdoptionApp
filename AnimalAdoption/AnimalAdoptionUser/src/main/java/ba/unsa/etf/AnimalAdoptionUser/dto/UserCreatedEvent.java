package ba.unsa.etf.AnimalAdoptionUser.dto;

import java.io.Serializable;

public class UserCreatedEvent implements Serializable {

    private Long userId;
    private String email;
    private String fullName;

    public UserCreatedEvent() {}

    public UserCreatedEvent(Long userId, String email, String fullName) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }

    // GETTERI I SETTERI
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
