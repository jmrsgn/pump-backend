package com.johnmartin.pump.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnmartin.pump.entities.PostEntity;
import com.johnmartin.pump.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    public List<PostEntity> getAllPostsFromUser(UUID userId) {
        return postRepository.findAll().stream().filter(post -> post.getUserId().equals(userId)).toList();
    }

    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }

    public PostEntity getPostById(String postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
