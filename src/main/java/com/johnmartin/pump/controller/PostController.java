package com.johnmartin.pump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.request.CreatePostRequest;
import com.johnmartin.pump.dto.response.PostResponse;
import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.service.CommentService;
import com.johnmartin.pump.service.PostService;
import com.johnmartin.pump.service.UserService;
import com.johnmartin.pump.utils.ApiErrorUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiConstants.Path.API_POST)
public class PostController {

    private static final String DEBUG_TAG = PostController.class.getSimpleName();

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Result<List<PostResponse>>> getPosts() {
        List<PostResponse> posts = postService.getPostsWithLatestComments();
        return ResponseEntity.ok(Result.success(posts));
    }

    @PostMapping
    public ResponseEntity<Result<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse createdPost = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdPost));
    }

    @PostMapping(ApiConstants.Path.POST_LIKE)
    public ResponseEntity<Result<PostResponse>> likePost(@PathVariable(ApiConstants.Params.POST_ID) String postId) {
        // UserEntity user = getAuthenticatedUser();

        // try {
        // // Get post info
        // PostEntity post = postService.getPostById(postId);
        // if (post == null) {
        // return ApiErrorUtils.createNotFoundErrorResponse(ApiErrorMessages.Post.POST_NOT_FOUND);
        // } else {
        // // If user already liked the post, unlike
        // if (CollectionUtils.containsAny(post.getLikedUserIds(), user.getId())) {
        // postService.unlikePost(postId, user.getId());
        // } else {
        // postService.likePost(postId, user.getId());
        // }
        // List<CommentEntity> commentEntityList = commentService.getCommentsFromPost(post.getId());
        // List<CommentResponse> commentResponseList = new ArrayList<>(commentEntityList.stream()
        // .map(CommentMapper::toResponse)
        // .toList());
        // PostResponse updatedPost = PostMapper.toResponse(postService.getPostById(postId),
        // commentResponseList,
        // user.getId());
        // return ResponseEntity.ok(Result.success(updatedPost));
        // }
        // } catch (Exception e) {
        // LoggerUtility.e(DEBUG_TAG, e.getMessage(), e);
        // return ApiErrorUtils.createInternalServerErrorResponse(ApiErrorMessages.INTERNAL_SERVER_ERROR);
        // }

        return ApiErrorUtils.createInternalServerErrorResponse(ApiErrorMessages.INTERNAL_SERVER_ERROR);

    }
}
