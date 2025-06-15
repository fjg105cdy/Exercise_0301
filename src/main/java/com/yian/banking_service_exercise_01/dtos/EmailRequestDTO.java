package com.yian.banking_service_exercise_01.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequestDTO {
    @NotBlank(message="Email is not empty")
    @Email(message="Email is invalid")
    private String email;
}
