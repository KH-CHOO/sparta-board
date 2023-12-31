package com.sparta.spartaboard.service;

import com.sparta.spartaboard.domain.dto.LoginRequestDto;
import com.sparta.spartaboard.domain.dto.SignupRequestDto;
import com.sparta.spartaboard.domain.entity.User;
import com.sparta.spartaboard.domain.dto.CommonResponseDto;
import com.sparta.spartaboard.repository.UserRepository;
import com.sparta.spartaboard.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<CommonResponseDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> checkUsername = userRepository.findById(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);

        return ResponseEntity.status(201).body(new CommonResponseDto(201, "회원가입이 완료되었습니다."));
    }


    public ResponseEntity<CommonResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findById(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(username);
        jwtUtil.addJwtToHeader(token, res);

        return ResponseEntity.ok(new CommonResponseDto(200, "로그인되었습니다."));
    }


}
