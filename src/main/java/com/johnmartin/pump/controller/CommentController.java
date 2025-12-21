package com.johnmartin.pump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.AuthUser;
import com.johnmartin.pump.dto.request.CreateCommentRequest;
import com.johnmartin.pump.dto.response.CommentResponse;
import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.service.CommentService;
import com.johnmartin.pump.utils.AuthUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
@RestController
@RequestMapping(ApiConstants.Path.API_COMMENT)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Result<CommentResponse>> createComment(@PathVariable(ApiConstants.Params.POST_ID) String postId,
                                                                 @Valid @RequestBody CreateCommentRequest createCommentRequest,
                                                                 HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        CommentResponse createdComment = commentService.createComment(authUser, postId, createCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdComment));
    }

    @DeleteMapping(ApiConstants.Path.COMMENT_INFO)
    public ResponseEntity<Result<Void>> deleteComment(@PathVariable(ApiConstants.Params.POST_ID) @NotBlank(message = ApiErrorMessages.Post.POST_ID_IS_REQUIRED) String postId,
                                                      @PathVariable(ApiConstants.Params.COMMENT_ID) @NotBlank(message = ApiErrorMessages.Comment.COMMENT_ID_IS_REQUIRED) String commentId,
                                                      HttpServletRequest request) {
        AuthUser authUser = AuthUtils.requireAuthUser(request);
        commentService.deleteComment(authUser, postId, commentId);
        return ResponseEntity.ok(Result.success(null));
    }

}
