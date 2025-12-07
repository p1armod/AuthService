package org.auth.DTO.SignIn;

import lombok.Data;

@Data
public class SignInRequestDTO {
    private String username;
    private String password;
}
