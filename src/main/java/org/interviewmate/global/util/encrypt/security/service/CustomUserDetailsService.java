package org.interviewmate.global.util.encrypt.security.service;

import static org.interviewmate.global.error.ErrorCode.INVALID_AUTHENTICATION;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.util.encrypt.security.exception.SecurityException;
import org.interviewmate.global.util.encrypt.security.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new SecurityException(INVALID_AUTHENTICATION)
        );

        return CustomUserDetails.of(user);
    }

}
