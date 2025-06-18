package com.yian.banking_service_exercise_01.controllers;

import com.yian.banking_service_exercise_01.dtos.common.ApiResponseDTO;
import com.yian.banking_service_exercise_01.dtos.auth.AuthRequestDTO;
import com.yian.banking_service_exercise_01.dtos.auth.AuthResponseDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserResponseDTO;
import com.yian.banking_service_exercise_01.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor

@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> loginUser(
            @RequestBody @Valid AuthRequestDTO authRequestDTO
    ){
        System.out.println("authRequestDTO: " + authRequestDTO);

        AuthResponseDTO authResponseDTO = userService.login(authRequestDTO);

        ApiResponseDTO<AuthResponseDTO> response = ApiResponseDTO.<AuthResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("succesfully logged in")
                .data(authResponseDTO)
                .build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserInfoByToken(){
        UserResponseDTO userResponseDTO = userService.getUserInfoByToken();
        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("succesfully get user info")
                .data(userResponseDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
