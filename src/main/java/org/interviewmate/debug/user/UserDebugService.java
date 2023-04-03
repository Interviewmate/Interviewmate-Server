package org.interviewmate.debug.user;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDebugService {
    private final UserRepository userRepository;


    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_DATA));
    }
}
