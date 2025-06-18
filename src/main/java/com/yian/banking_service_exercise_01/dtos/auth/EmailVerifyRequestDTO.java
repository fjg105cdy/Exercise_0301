package com.yian.banking_service_exercise_01.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerifyRequestDTO {
    @NotBlank(message="Email must not be empty")
    @Email(message="Email is not valid")
    @Schema(description = "이메일 주소", example = "yian.choi05@gmail.com")
    private String email;

    @NotBlank(message="Code must not be empty")
    @Size(min=6,max=6,message="Code must be exactly 6 digits")
    @Schema(description = "인증번호 6자리", example = "000000")
    private String code;
}
