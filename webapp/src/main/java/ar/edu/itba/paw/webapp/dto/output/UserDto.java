package ar.edu.itba.paw.webapp.dto.output;
import javax.ws.rs.core.UriInfo;
import ar.edu.itba.paw.model.User;

import java.net.URI;

public class UserDto {

    private String username;
    private String email;
    private boolean enabled;

    public static UserDto fromUser(User user, UriInfo uri) {
        final UserDto dto = new UserDto();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.enabled = user.isEnabled();
        return dto;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
