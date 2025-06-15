package com.yian.banking_service_exercise_01.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String accessToken; //10min/1h/10h/1d
    private String refreshToken;//2w/1m
}
