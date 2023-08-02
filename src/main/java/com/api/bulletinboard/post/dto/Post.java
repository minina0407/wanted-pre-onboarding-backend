package com.api.bulletinboard.post.dto;


import com.api.bulletinboard.post.entity.PostEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    private Long id;
    private Long userId;
    private String title;
    @NotBlank(message = "내용이 없습니다.")
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Post(Long id, Long userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post fromEntity(PostEntity postEntity) {
        return Post.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .userId(postEntity.getUser().getId())
                .createdAt(postEntity.getCreatedAt())
                .updatedAt(postEntity.getUpdatedAt())
                .build();
    }
}


