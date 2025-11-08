package com.johnmartin.pump.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.constants.ApiErrorMessages;
import com.johnmartin.pump.dto.response.ApiErrorResponse;
import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.entities.PostEntity;
import com.johnmartin.pump.service.PostService;

@RestController
@RequestMapping(ApiConstants.Path.API_POST)
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Result<List<PostEntity>>> getAllPosts() {
        try {
            List<PostEntity> posts = Optional.ofNullable(postService.getAllPosts()).orElse(Collections.emptyList());

            if (posts.isEmpty()) {
                Result<List<PostEntity>> result = Result.failure(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                                                                                      ApiErrorMessages.Post.NO_POSTS_FOUND,
                                                                                      ApiErrorMessages.Post.THERE_ARE_NO_POST_AVAILABLE));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }

            Result<List<PostEntity>> result = Result.success(posts);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Result<List<PostEntity>> result = Result.failure(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                                                  ApiErrorMessages.INTERNAL_SERVER_ERROR,
                                                                                  e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping
    public ResponseEntity<Result<PostEntity>> createPost(@RequestBody PostEntity post) {
        try {
            // Save the post
            PostEntity createdPost = postService.createPost(post);
            Result<PostEntity> result = Result.success(createdPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            Result<PostEntity> result = Result.failure(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                                            ApiErrorMessages.INTERNAL_SERVER_ERROR,
                                                                            e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
