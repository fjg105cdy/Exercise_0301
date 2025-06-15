package com.yian.banking_service_exercise_01.controllers;

import com.yian.banking_service_exercise_01.dtos.ApiResponseDTO;
import com.yian.banking_service_exercise_01.dtos.AuthRequestDTO;
import com.yian.banking_service_exercise_01.dtos.AuthResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> loginUser(
            @RequestBody @Valid AuthRequestDTO authRequestDTO
    ){

    }
}
