package com.yian.banking_service_exercise_01.controllers;

import com.yian.banking_service_exercise_01.dtos.*;
import com.yian.banking_service_exercise_01.services.EmailService;
import com.yian.banking_service_exercise_01.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        emailService
                .sendEmail(
                        userRequestDTO.getEmail(),
                        userRequestDTO.getUsername(),
                        "Welcome " + userRequestDTO.getUsername()
                );
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id){
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable String id,
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted user successfully.");
    }
    @GetMapping("/pagination")
    public ResponseEntity<PageResponseDTO> getUsersWithPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue="10") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required=false) String searchKeyword
    ){PageResponseDTO pageResponseDTO = userService.getUsersWithPagination(pageNo,pageSize,sortBy,sortDir,searchKeyword);
        return ResponseEntity.ok(pageResponseDTO);
    }

    @PostMapping("/send/email")
    public ResponseEntity<ApiResponseDTO<Boolean>> sendEmail(
            @RequestBody @Valid EmailRequestDTO emailRequestDTO
    ){
        userService.sendEmailVerification(emailRequestDTO);

        ApiResponseDTO<Boolean> response = ApiResponseDTO.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Email sent successfully")
                .data(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify/email")
    public ResponseEntity<ApiResponseDTO<Boolean>> verifyEmail(
            @RequestBody @Valid EmailVerifyRequestDTO emailVerifyRequestDTO

    ){
        boolean verified = userService.verifyEmailCode(emailVerifyRequestDTO);
        ApiResponseDTO<Boolean> response = ApiResponseDTO.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("successful verify email")
                .build();
        return ResponseEntity.ok(response);
    }

}
