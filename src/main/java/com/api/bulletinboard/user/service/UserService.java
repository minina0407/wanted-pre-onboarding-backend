package com.api.bulletinboard.user.service;


import com.api.bulletinboard.auth.enums.AuthEnums;
import com.api.bulletinboard.auth.utils.SecurityUtil;
import com.api.bulletinboard.exception.dto.BadRequestException;
import com.api.bulletinboard.exception.dto.ExceptionEnum;
import com.api.bulletinboard.user.dto.User;
import com.api.bulletinboard.user.entity.UserEntity;
import com.api.bulletinboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public UserEntity getMyUserWithAuthorities() {
        UserEntity userEntity = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RESPONSE_NOT_FOUND));

        return userEntity;
    }

    @Transactional
    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException(ExceptionEnum.REQUEST_PARAMETER_INVALID, "이미 가입되어 있는 이메일입니다.");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(AuthEnums.ROLE.ROLE_USER)
                .build();

        userRepository.save(userEntity);
    }

}
