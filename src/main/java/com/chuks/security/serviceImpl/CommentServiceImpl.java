package com.chuks.security.serviceImpl;

import com.chuks.security.dto.CommentDTO;
import com.chuks.security.exception.InvalidInputException;
import com.chuks.security.exception.PostNotFoundException;
import com.chuks.security.exception.ResourceNotFoundException;
import com.chuks.security.entity.Comment;
import com.chuks.security.entity.Post;
import com.chuks.security.entity.User;
import com.chuks.security.repository.CommentRepo;
import com.chuks.security.repository.PostRepository;
import com.chuks.security.repository.UserRepository;
import com.chuks.security.services.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final HttpSession session;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer id) {
        if (session.getAttribute("userId") == null) {
            throw new RuntimeException("Please login to the application.");
        }
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist"));
        Post commentPost = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        Comment newComment = modelMapper.map(commentDTO, Comment.class);
        newComment.setPost(commentPost);
        newComment.setUser(user);
        Comment savedComment = commentRepo.save(newComment);

        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer id) throws InvalidInputException, PostNotFoundException {
        if (session.getAttribute("userId") ==null) {
            throw new RuntimeException("Please login to the application");
        }
        if (commentDTO.getBody().equals("")){
            throw new InvalidInputException("Please write a comment");
        }
        Comment existingComment = commentRepo.findById(id).orElseThrow(()-> {
            String message = "Can't find existing comment";
            return new PostNotFoundException(message);
        });
        existingComment.setBody(commentDTO.getBody());
        commentRepo.save(existingComment);
        return modelMapper.map(existingComment, CommentDTO.class);
    }

    @Override
    public void deleteCommentById(Integer id) {
        Comment comment = commentRepo.findById(id).orElseThrow(()-> new
                RuntimeException("Please login to the app to execute this function" ));
        commentRepo.delete(comment);
    }
}
