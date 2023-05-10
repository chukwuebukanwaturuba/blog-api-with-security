package com.chuks.security.serviceImpl;

import com.chuks.security.dto.LikeDTO;
import com.chuks.security.exception.PostNotFoundException;
import com.chuks.security.entity.Comment;
import com.chuks.security.entity.Like;
import com.chuks.security.entity.Post;
import com.chuks.security.entity.User;
import com.chuks.security.repository.CommentRepo;
import com.chuks.security.repository.LikeRepo;
import com.chuks.security.repository.PostRepository;
import com.chuks.security.repository.UserRepository;
import com.chuks.security.services.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepo likeRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final CommentRepo commentRepo;
    private final HttpSession session;

    @Override
    public LikeDTO createPostLike(Integer postId,LikeDTO likeDTO) throws PostNotFoundException, RuntimeException {
        Integer userId = getLoggedInUser();
        if (session.getAttribute("userId") == null) {
            throw new RuntimeException("Please login to the application");
        }
        Post foundPost = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        User foundUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = new Like();
        like.setChecked(likeDTO.getChecked());
        like.setPost(foundPost);
        like.setUser(foundUser);
        likeRepo.save(like);
        return mapToDTO(like);
    }
    @Override
    public LikeDTO createCommentLike(Integer commentId) throws RuntimeException {
        Integer userId = getLoggedInUser();
        if (session.getAttribute("userId") == null) {
            throw new RuntimeException("Please login to the application");
        }
        Comment foundComment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User foundUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = new Like();
        like.setComment(foundComment);
        like.setUser(foundUser);
        likeRepo.save(like);
        return mapToDTO(like);
    }
    private Integer getLoggedInUser() {
        return (Integer) session.getAttribute("userId");
    }
    private LikeDTO mapToDTO(Like like){
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setPostId(like.getPost().getId());
        likeDTO.setUserId(like.getUser().getId());
//        likeDTO.setCommentId(like.getComment().getId());
        return likeDTO;
    }
}
