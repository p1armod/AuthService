package org.auth.DTO.SignUp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.auth.Entity.Role;

import java.util.List;
import java.util.Set;

@Data
public class SignUpRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @NotNull
    private Set<String> roles;


}
