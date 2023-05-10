package com.chuks.security.services;

import com.chuks.security.dto.CommentDTO;
import com.chuks.security.exception.InvalidInputException;
import com.chuks.security.exception.PostNotFoundException;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer id);
    CommentDTO updateComment(CommentDTO commentDTO, Integer id) throws InvalidInputException, PostNotFoundException;
    void deleteCommentById(Integer id);
}
