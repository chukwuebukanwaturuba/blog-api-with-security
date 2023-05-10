package com.chuks.security.repository;

import com.chuks.security.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    //List<Comment> findByPost_IdOrderByCreatedAt(Long postId);
}
