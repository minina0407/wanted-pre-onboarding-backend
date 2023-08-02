package com.api.bulletinboard.post.service;

import com.api.bulletinboard.exception.dto.BadRequestException;
import com.api.bulletinboard.exception.dto.ExceptionEnum;
import com.api.bulletinboard.post.dto.Posts;
import com.api.bulletinboard.post.repository.PostRepository;
import com.api.bulletinboard.post.dto.Post;
import com.api.bulletinboard.post.entity.PostEntity;
import com.api.bulletinboard.user.entity.UserEntity;
import com.api.bulletinboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public void savePost(Post post) {
        UserEntity user = userService.getMyUserWithAuthorities();
        PostEntity savedPost = PostEntity.builder()
                .user(user)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(savedPost);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {

        return Post.fromEntity(postRepository.findPostById(postId)
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RESPONSE_NOT_FOUND, "게시글을 찾을 수 없습니다.")));
    }

    @Transactional(readOnly = true)
    public Posts getAllPosts(Pageable pageable) {
        Page<PostEntity> postEntities = postRepository.findAll(pageable);

        if (postEntities.isEmpty()) {
            throw new BadRequestException(ExceptionEnum.RESPONSE_NOT_FOUND, "게시글이 없습니다.");
        }

        List<Post> postList = postEntities.stream().map(Post::fromEntity).collect(Collectors.toList());
        Posts posts = Posts.builder()
                .posts(postList)
                .totalPages(postEntities.getTotalPages())
                .totalElements(postEntities.getNumberOfElements())
                .total(postEntities.getTotalElements())
                .build();
        return posts ;
    }

    @Transactional
    public void updatePost(Long id, Post post) {
        UserEntity currentUser = userService.getMyUserWithAuthorities();

        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RESPONSE_NOT_FOUND, "게시글을 찾을 수 없습니다."));

        if (!postEntity.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException(ExceptionEnum.RESPONSE_UNAUTHORIZED, "게시글 작성자만 수정할 수 있습니다.");
        }

        postEntity.updateContent(post.getContent(), LocalDateTime.now());
        postRepository.save(postEntity);

    }

    @Transactional
    public void deletePost(Long id) {
        UserEntity currentUser = userService.getMyUserWithAuthorities();
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RESPONSE_NOT_FOUND, "게시글을 찾을 수 없습니다."));

        if (!postEntity.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException(ExceptionEnum.RESPONSE_UNAUTHORIZED, "게시글 작성자만 수정할 수 있습니다.");
        }

        postRepository.delete(postEntity);
    }
}
