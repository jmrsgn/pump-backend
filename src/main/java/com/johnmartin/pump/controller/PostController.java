package com.johnmartin.pump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.AuthUser;
import com.johnmartin.pump.dto.request.CreatePostRequest;
import com.johnmartin.pump.dto.request.UpdatePostRequest;
import com.johnmartin.pump.dto.response.PostResponse;
import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.service.PostService;
import com.johnmartin.pump.service.facade.PostCommentFacade;
import com.johnmartin.pump.utils.AuthUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping(ApiConstants.Path.API_POST)
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostCommentFacade postCommentFacade;

    @GetMapping
    public ResponseEntity<Result<List<PostResponse>>> getPosts(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                               HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        List<PostResponse> posts = postCommentFacade.getPostsWithLatestComments(authUser, page);
        return ResponseEntity.ok(Result.success(posts));
    }

    @GetMapping(ApiConstants.Path.POST_INFO)
    public ResponseEntity<Result<PostResponse>> getPostInfo(@PathVariable @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                            HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        PostResponse response = postCommentFacade.getPostInfo(authUser, postId);
        return ResponseEntity.ok(Result.success(response));
    }

    @PostMapping
    public ResponseEntity<Result<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest createPostRequest,
                                                           HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        PostResponse createdPost = postService.createPost(authUser, createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdPost));
    }

    @PostMapping(ApiConstants.Path.POST_LIKE)
    public ResponseEntity<Result<PostResponse>> likePost(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                         HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        PostResponse response = postCommentFacade.likePost(authUser, postId);
        return ResponseEntity.ok(Result.success(response));
    }

    @DeleteMapping(ApiConstants.Path.POST_INFO)
    public ResponseEntity<Result<Void>> deletePost(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                   HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        postCommentFacade.deletePost(authUser, postId);
        return ResponseEntity.ok(Result.success(null));
    }

    @PutMapping(ApiConstants.Path.POST_INFO)
    public ResponseEntity<Result<PostResponse>> updatePost(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                           @Valid @RequestBody UpdatePostRequest updatePostRequest,
                                                           HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        PostResponse response = postCommentFacade.updatePost(authUser, postId, updatePostRequest);
        return ResponseEntity.ok(Result.success(response));
    }
}
