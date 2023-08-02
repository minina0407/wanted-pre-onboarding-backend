package com.api.bulletinboard.post.dto;


import com.api.bulletinboard.post.entity.PostEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post{
    private Long id;
    private Long userId;
    @NotBlank(message = "내용이 없습니다.")
    private String content;
    private Date createdAt;

    @Builder
    public Post(Long id, Long userId, String content, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Post fromEntity(PostEntity postEntity) {

        return Post.builder()
                .id(postEntity.getId())
                .content(postEntity.getContent())
                .userId(postEntity.getUser().getId())
                .build();
    }
}


