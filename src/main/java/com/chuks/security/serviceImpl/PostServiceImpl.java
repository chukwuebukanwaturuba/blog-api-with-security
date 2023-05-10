package com.chuks.security.serviceImpl;

import com.chuks.security.dto.PostDTO;
import com.chuks.security.exception.ResourceNotFoundException;
import com.chuks.security.entity.Post;
import com.chuks.security.entity.User;
import com.chuks.security.repository.PostRepository;
import com.chuks.security.repository.UserRepository;
import com.chuks.security.services.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepository;
    private final HttpSession session;
    @Autowired
    public PostServiceImpl(PostRepository postRepo, UserRepository userRepo, HttpSession session) {
        this.postRepo = postRepo;
        this.userRepository = userRepo;
        this.session = session;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId) {
        if (session.getAttribute("userId") == null) {
            throw new RuntimeException("Please login to the application.");
        }
        Post post = mapToEntity(postDTO);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist"));
        post.setUser(user);
        Post newPost =postRepo.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepo.findAll();
       return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepo.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }


    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
      //  postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }
}
