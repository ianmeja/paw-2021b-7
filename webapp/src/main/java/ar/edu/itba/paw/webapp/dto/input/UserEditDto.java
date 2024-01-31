package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditDto {

    @NotNull
    @Size(min = 4, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
}
