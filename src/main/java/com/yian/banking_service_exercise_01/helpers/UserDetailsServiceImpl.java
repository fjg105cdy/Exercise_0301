package com.yian.banking_service_exercise_01.helpers;

import com.yian.banking_service_exercise_01.entities.User;
import com.yian.banking_service_exercise_01.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entered UserDetailsServiceImpl.loadUserByUsername");

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email:" + username));
        logger.info("User Authenticated Successfully !");

        return new CustomUserDetails(user);
    }
}
