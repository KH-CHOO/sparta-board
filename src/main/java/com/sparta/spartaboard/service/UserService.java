package com.sparta.spartaboard.service;

import com.sparta.spartaboard.domain.dto.UserRequestDto;
import com.sparta.spartaboard.domain.entity.User;
import com.sparta.spartaboard.domain.dto.UserResponseDto;
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

    public ResponseEntity<UserResponseDto> login(UserRequestDto userRequestDto, HttpServletResponse res) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findById(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(username);
        jwtUtil.addJwtToHeader(token, res);

        return ResponseEntity.ok(new UserResponseDto(200, "로그인되었습니다."));
    }
}
