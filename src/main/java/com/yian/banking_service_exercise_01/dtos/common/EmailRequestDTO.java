package com.yian.banking_service_exercise_01.dtos.common;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "이메일 주소", example = "yian.choi05@gmail.com")
    private String email;
}
