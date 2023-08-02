package com.api.bulletinboard.post.controller;


import com.api.bulletinboard.post.dto.Post;
import com.api.bulletinboard.post.dto.Posts;
import com.api.bulletinboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController{

    private final PostService postService;

    @GetMapping("/{postId}")
       public ResponseEntity<Post> getPostById(
            @PathVariable("postId") Long postId
    ) {
        Post post =postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Posts> getAllPosts(Pageable pageable) {
        Posts posts = postService.getAllPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Post> savePost(
            @RequestBody Post post) {
        postService.savePost(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Post> updatePost(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody Post post
    ) {
        postService.updatePost(postId, post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity deletePost(
            @PathVariable("postId") Long postId
    ) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
