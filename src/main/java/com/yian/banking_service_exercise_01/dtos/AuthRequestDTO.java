package com.yian.banking_service_exercise_01.dtos;

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
public class AuthRequestDTO {
    @NotBlank(message = "email must not be empty")
    @Email(message = "email is not valid")
    @Schema(description = "이메일 주소", example = "yian.choi05@gmail.com")
    private String email;

    @NotBlank(message = "password must not be empty")
    @Size(min = 8, max = 16, message= "password must be between 8 and 16 characters")
    @Schema(description = "비밀번호(8-16자, 대/소문자+숫자+특수문자 조합 권장", example = "P@ssw0rd123")
    private String password;
}
