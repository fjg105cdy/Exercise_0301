package com.yian.banking_service_exercise_01.controllers;

import com.yian.banking_service_exercise_01.dtos.common.ApiResponseDTO;
import com.yian.banking_service_exercise_01.dtos.common.EmailRequestDTO;
import com.yian.banking_service_exercise_01.dtos.auth.EmailVerifyRequestDTO;
import com.yian.banking_service_exercise_01.dtos.common.PageResponseDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserRequestDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserResponseDTO;
import com.yian.banking_service_exercise_01.entities.User;
import com.yian.banking_service_exercise_01.services.EmailService;
import com.yian.banking_service_exercise_01.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        ApiResponseDTO<List<UserResponseDTO>> response = ApiResponseDTO.<List<UserResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("get all users")
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        emailService
                .sendEmail(
                        userRequestDTO.getEmail(),
                        userRequestDTO.getUsername(),
                        "Welcome " + userRequestDTO.getUsername()
                );
        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("created user")
                .data(userResponseDTO)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(@PathVariable String id){
        UserResponseDTO user = userService.getUserById(id);
        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("got user")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
            @PathVariable String id,
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("updated user")
                .data(updatedUser)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteUserById(@PathVariable String id){
        userService.deleteUser(id);
        ApiResponseDTO<String> response = ApiResponseDTO.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("deleted user")
                .data("deleted")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponseDTO<PageResponseDTO>> getUsersWithPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue="10") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required=false) String searchKeyword
    ){PageResponseDTO pageResponseDTO = userService.getUsersWithPagination(pageNo,pageSize,sortBy,sortDir,searchKeyword);
        ApiResponseDTO<PageResponseDTO> response = ApiResponseDTO.<PageResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("got users data")
                .data(pageResponseDTO)
                .build();
        return ResponseEntity.ok(response);
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
