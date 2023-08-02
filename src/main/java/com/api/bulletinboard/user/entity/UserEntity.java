package com.api.bulletinboard.user.entity;


import com.api.bulletinboard.auth.enums.AuthEnums;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthEnums.ROLE role;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public UserEntity(Long id,  String password, String email,  AuthEnums.ROLE role , LocalDateTime createdAt){
        this.id = id;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

}
