package com.chuks.security.controller;

import com.chuks.security.dto.LikeDTO;
import com.chuks.security.exception.PostNotFoundException;
import com.chuks.security.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/post/{post-id}/like_post")
    public ResponseEntity<LikeDTO> likePost(@PathVariable("post-id") Integer postId, @RequestBody LikeDTO likeDTO) throws PostNotFoundException {
        LikeDTO likeDTOs = likeService.createPostLike(postId,likeDTO);
        return new ResponseEntity<>(likeDTOs, HttpStatus.OK);
    }
    @PostMapping("/comment/{comment-id}")
    public ResponseEntity<LikeDTO> likeComment(@PathVariable("comment-id") Integer commentId) throws PostNotFoundException {
        LikeDTO likeDTO = likeService.createCommentLike(commentId);
        return new ResponseEntity<>(likeDTO, HttpStatus.OK);
    }
}
