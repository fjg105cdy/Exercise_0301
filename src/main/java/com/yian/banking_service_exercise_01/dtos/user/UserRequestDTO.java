package com.yian.banking_service_exercise_01.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRequestDTO {
    @NotBlank(message = "Username is required")
    @Schema(description = "username", example = "yian")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    @Schema(description = "email", example = "yian.choi05@gmail.com")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 16, message="password must be between 8 and 16 characters")
    @Schema(description = "password", example = "P@ssw0rd123")
    private String password;

    private String description;
}
