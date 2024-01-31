package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserCreateDto {

    @NotNull
    @Size(min = 4, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String username;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*")
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
