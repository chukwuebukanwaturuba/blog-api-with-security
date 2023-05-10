package com.chuks.security.services;

import com.chuks.security.dto.LikeDTO;
import com.chuks.security.exception.PostNotFoundException;

public interface LikeService {

    LikeDTO createPostLike(Integer postId,LikeDTO likeDTO) throws PostNotFoundException;

    LikeDTO createCommentLike(Integer commentId) throws PostNotFoundException;
}
