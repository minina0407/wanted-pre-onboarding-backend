package com.api.bulletinboard.user.dto;

import com.api.bulletinboard.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {
    private Long id;
    private String nickname;
    @Email(message = "올바른 이메일을 입력해주세요")
    private String email;
    private String name;
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
    @Builder
    public User(Long id, String nickname, String email, String name, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static User fromEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .build();
    }
}