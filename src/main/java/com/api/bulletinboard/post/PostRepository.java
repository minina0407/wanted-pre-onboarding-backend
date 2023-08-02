package com.api.bulletinboard.post;


import com.api.bulletinboard.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findPostById(Long id);

}

