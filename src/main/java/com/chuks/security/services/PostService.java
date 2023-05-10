package com.chuks.security.services;

import com.chuks.security.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO, Integer id);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Integer id);
    PostDTO updatePost(PostDTO postDTO, Integer id);
    void deletePostById(Integer id);
}
