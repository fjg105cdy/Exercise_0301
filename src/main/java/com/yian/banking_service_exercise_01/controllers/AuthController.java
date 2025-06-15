package com.yian.banking_service_exercise_01.controllers;

import com.yian.banking_service_exercise_01.dtos.ApiResponseDTO;
import com.yian.banking_service_exercise_01.dtos.AuthRequestDTO;
import com.yian.banking_service_exercise_01.dtos.AuthResponseDTO;
import com.yian.banking_service_exercise_01.entities.User;
import com.yian.banking_service_exercise_01.services.JwtService;
import com.yian.banking_service_exercise_01.services.UserService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor

@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> loginUser(
            @RequestBody @Valid AuthRequestDTO authRequestDTO
    ){
        System.out.println("authRequestDTO: " + authRequestDTO);

        Authentication authentication
                = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
        );
        if (authentication.isAuthenticated()) {
            User user = userService.findByEmail(authRequestDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String accessToken = jwtService.generateToken(user.getEmail());
            String refreshToken = jwtService.generateToken(user.getEmail());

            AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            ApiResponseDTO<AuthResponseDTO> response = ApiResponseDTO.<AuthResponseDTO>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Successfully logged in")
                    .data(authResponseDTO)
                    .build();
            return ResponseEntity.ok(response);
        } else{
            throw new UsernameNotFoundException("User not found");
        }

    }
}
