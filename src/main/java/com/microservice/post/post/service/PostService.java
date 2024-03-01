package com.microservice.post.post.service;

import com.microservice.post.post.config.RestTemplateConfig;
import com.microservice.post.post.entity.Post;
import com.microservice.post.post.payload.PostDto;
import com.microservice.post.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private RestTemplateConfig restTemplate;
    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post savedPost = postRepo.save(post);
        return savedPost;
    }
    public Post findPostById(String postId){
      Post post = postRepo.findById(postId).get();
      return post;
    }

    public PostDto getPostWithComments(String postId) {
        Post post = postRepo.findById(postId).get();
        ArrayList comments = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
         PostDto postDto = new PostDto();
         postDto.setPostId(post.getId());
         postDto.setTitle(post.getTitle());
         postDto.setDescription(post.getDescription());
         postDto.setContent(post.getContent());
         postDto.setComments(comments);
         return postDto;

    }
}
