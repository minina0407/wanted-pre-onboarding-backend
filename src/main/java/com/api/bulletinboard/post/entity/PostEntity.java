package com.api.bulletinboard.post.entity;

import com.api.bulletinboard.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @Column(name = "content", nullable = false)
    private String content;


    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;


    @Builder
    public PostEntity(Long id, UserEntity user, String content, Date createdAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }
    public void updateContent(String content) {
        this.content = content;
    }
   }
