package org.interviewmate.domain.auth.service;

import static org.interviewmate.global.error.ErrorCode.NOT_EXIST_USER;
import static org.interviewmate.global.error.ErrorCode.WRONG_PASSWORD;
import static org.interviewmate.global.util.encrypt.Secret.PASSWORD_KEY;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interviewmate.domain.auth.dto.reponse.LoginRes;
import org.interviewmate.domain.auth.dto.request.LoginReq;
import org.interviewmate.domain.user.exception.UserException;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.domain.user.repository.UserRepository;
import org.interviewmate.global.util.encrypt.AES128;
import org.interviewmate.global.util.jwt.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
//    private final JwtService jwtService;

    public LoginRes signUp(LoginReq loginReq) {

        // 이메일로 User 찾기
        User findUser = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));

        // 찾은 유저에서 비밀번호 추출
        String password = new AES128(PASSWORD_KEY).decrypt(findUser.getPassword());
        log.info(password);
        log.info(loginReq.getPassword());

        // 요청 비밀번호와 같은지 확인
        if (password.equals(loginReq.getPassword())) {
            throw new UserException(WRONG_PASSWORD);
        }

        // todo: jwt 토큰(access, refresh) 발급
//        findUser.setRefreshToken("1");
//        findUser.setAccessToken("1");
        userRepository.save(findUser);

        return LoginRes.of(findUser);

    }

}
