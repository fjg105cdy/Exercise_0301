package com.yian.banking_service_exercise_01.services;

import com.yian.banking_service_exercise_01.dtos.common.EmailRequestDTO;
import com.yian.banking_service_exercise_01.dtos.auth.EmailVerifyRequestDTO;
import com.yian.banking_service_exercise_01.dtos.common.PageResponseDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserRequestDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserResponseDTO;
import com.yian.banking_service_exercise_01.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserByEmail(String email);
    UserResponseDTO getUserById(String id);
    UserResponseDTO updateUser(String id, UserRequestDTO userRequestDTO);
    void deleteUser(String id);
    PageResponseDTO getUsersWithPagination(int pageNo, int pageSize, String sortBy, String sortDir, String searchKeyword);

    String sendEmailVerification(EmailRequestDTO emailRequestDTO);
    boolean verifyEmailCode(EmailVerifyRequestDTO emailVerifyRequestDTO);

    Optional<User> findByEmail(String email);
}
