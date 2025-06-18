package com.yian.banking_service_exercise_01.services.impl;

import com.yian.banking_service_exercise_01.dtos.common.EmailRequestDTO;
import com.yian.banking_service_exercise_01.dtos.auth.EmailVerifyRequestDTO;
import com.yian.banking_service_exercise_01.dtos.common.PageResponseDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserRequestDTO;
import com.yian.banking_service_exercise_01.dtos.user.UserResponseDTO;
import com.yian.banking_service_exercise_01.entities.User;
import com.yian.banking_service_exercise_01.exceptions.ResourceNotFoundException;
import com.yian.banking_service_exercise_01.mappers.UserMapper;
import com.yian.banking_service_exercise_01.repositories.UserRepository;
import com.yian.banking_service_exercise_01.services.EmailService;
import com.yian.banking_service_exercise_01.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        //email 유무 체크
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("User with email " + userRequestDTO.getEmail() + " already exists");
        }

        //convert DTO to Entity
        User user = userMapper.mapToUserEntity(userRequestDTO);
        //save
        User savedUser = userRepository.save(user);

        emailService.sendEmail(savedUser.getEmail(), savedUser.getUsername(),"Welcome to Yian");

        //convert Entity to DTO
        return userMapper.mapToUserResponseDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        return userMapper.mapToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(String id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        if (!user.getUsername().equals(userRequestDTO.getUsername())) {
            throw new ResourceNotFoundException("product", "id", id);
        }
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());

        User savedUser = userRepository.save(user);
        return userMapper.mapToUserResponseDTO(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user ","id",id));
        userRepository.delete(user);

    }

    @Override
    public PageResponseDTO getUsersWithPagination(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir,
            String searchKeyword){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<User> users;
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            users = userRepository.findAll(pageable);
        } else{
            users=userRepository.findByUsernameContainingIgnoreCase(searchKeyword, pageable);
        }
        List<UserResponseDTO> userResponseDTOS = users
                .getContent()
                .stream()
                .map(userMapper::mapToUserResponseDTO)
                .collect(Collectors.toList());

        return PageResponseDTO.builder()
                .body(userResponseDTOS)
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .hasNextPage(users.hasNext())
                .hasPreviousPage(users.hasPrevious())
                .pageNo(users.getNumber())
                .pageSize(users.getSize())
                .build();
    }

    @Override
    public String sendEmailVerification(EmailRequestDTO emailRequestDTO) {
        //6자리로 된 랜덤번호 생성 -> 이메일로 보내기
        String code = String.format("%06d", new Random().nextInt(1000000));

        emailService.sendEmailVerificationCode(emailRequestDTO.getEmail(), code);

        redisTemplate.opsForValue().set("email:verify:" + emailRequestDTO.getEmail(), code);
        String stored = (String) redisTemplate.opsForValue().get("email:verify:" + emailRequestDTO.getEmail());
        log.info("Redis에서 방금 저장된 값: {}", stored);

        return code;
    }

    @Override
    public boolean verifyEmailCode(EmailVerifyRequestDTO emailVerifyRequestDTO) {
        String redisKey="email:verify:" + emailVerifyRequestDTO.getEmail();
        String savedCode = (String) redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            throw new IllegalStateException("인증코드가 존재하지 않거나 만료되었습니다");
        }
        if (!savedCode.equals(emailVerifyRequestDTO.getCode())) {
            throw new IllegalStateException("인증코드가 일치하지 않습니다");
        }
        redisTemplate.delete(redisKey);
        return true;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
