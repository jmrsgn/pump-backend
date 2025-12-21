package com.johnmartin.pump.service.facade;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.johnmartin.pump.constants.UIConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.AuthUser;
import com.johnmartin.pump.dto.request.UpdatePostRequest;
import com.johnmartin.pump.dto.response.PostResponse;
import com.johnmartin.pump.entities.PostEntity;
import com.johnmartin.pump.exception.BadRequestException;
import com.johnmartin.pump.exception.UnauthorizedException;
import com.johnmartin.pump.mapper.PostMapper;
import com.johnmartin.pump.repository.PostRepository;
import com.johnmartin.pump.service.CommentService;
import com.johnmartin.pump.service.PostService;
import com.johnmartin.pump.utilities.LoggerUtility;

import jakarta.transaction.Transactional;

@Service
public class PostCommentFacade {

    private static final String DEBUG_TAG = PostCommentFacade.class.getSimpleName();

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    /**
     * Get posts with 10 latest comments
     * 
     * @param authUser
     *            - Authenticated user
     * @param page
     *            - page
     * @return List of PostResponse
     */
    public List<PostResponse> getPostsWithLatestComments(AuthUser authUser, int page) {
        LoggerUtility.d(DEBUG_TAG, String.format("Execute method: [getPostsWithLatestComments], page: [%d]", page));

        PageRequest pageRequest = PageRequest.of(page, UIConstants.MINIMUM_POSTS);
        Page<PostEntity> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageRequest);
        List<PostEntity> posts = postPage.getContent();

        return CollectionUtils.isEmpty(posts) ? Collections.emptyList()
                                              : posts.stream()
                                                     .map(post -> PostMapper.toResponse(post,
                                                                                        commentService.getComments(post.getId(),
                                                                                                                   0),
                                                                                        authUser.getId()))
                                                     .toList();
    }

    /**
     * Get post info
     * 
     * @param authUser
     *            - Authenticated user
     * @param postId
     *            - Post ID
     * @return PostResponse
     */
    public PostResponse getPostInfo(AuthUser authUser, String postId) {
        LoggerUtility.d(DEBUG_TAG, String.format("Execute method: [getPostInfo] postId: [%s]", postId));

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        PostEntity postToBeReturned = postService.getPostById(postId);
        LoggerUtility.d(DEBUG_TAG, String.format("postToBeReturned: [%s]", postToBeReturned));
        return PostMapper.toResponse(postToBeReturned, commentService.getComments(postId, 0), authUser.getId());
    }

    /**
     * Like a post
     * 
     * @param authUser
     *            - Authenticated user
     * @param postId
     *            - Post ID
     * @return PostResponse
     */
    public PostResponse likePost(AuthUser authUser, String postId) {
        LoggerUtility.d(DEBUG_TAG, String.format("Execute method: [likePost] postId: [%s]", postId));

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        PostEntity post = postService.getPostById(postId);

        // If user already liked the post, unlike
        if (CollectionUtils.containsAny(post.getLikedUserIds(), authUser.getId())) {
            postRepository.unlikePost(authUser.getId(), postId);
        } else {
            postRepository.likePost(authUser.getId(), postId);
        }

        // Get updated post to get updated like state
        PostEntity updatedPost = postService.getPostById(postId);
        LoggerUtility.v(DEBUG_TAG, String.format("updatedPost: [%s]", updatedPost));
        return PostMapper.toResponse(updatedPost, commentService.getComments(post.getId(), 0), authUser.getId());
    }

    /**
     * Delete a post
     * 
     * @param authUser
     *            - Authenticated user
     * @param postId
     *            - Post ID
     */
    @Transactional
    public void deletePost(AuthUser authUser, String postId) {
        LoggerUtility.d(DEBUG_TAG, String.format("Execute method: [deletePost] postId: [%s]", postId));

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        PostEntity post = postService.getPostById(postId);

        // Only post owner can delete
        if (!post.getUserId().equals(authUser.getId())) {
            throw new UnauthorizedException(ApiErrorMessages.User.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        // Delete all comments under the post
        commentService.deleteByPostId(postId);

        // Delete the post
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostResponse updatePost(AuthUser authUser, String postId, UpdatePostRequest request) {
        LoggerUtility.d(DEBUG_TAG,
                        String.format("Execute method: [updatePost] postId: [%s] request: [%s]", postId, request));

        if (StringUtils.isBlank(postId)) {
            throw new BadRequestException(ApiErrorMessages.Post.POST_ID_IS_REQUIRED);
        }

        if (request == null) {
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        PostEntity post = postService.getPostById(postId);

        // Only owner can edit
        if (!post.getUserId().equals(authUser.getId())) {
            throw new UnauthorizedException(ApiErrorMessages.User.YOU_ARE_NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION);
        }

        // Update allowed fields only
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setUpdatedAt(Instant.now());

        PostEntity updatedPost = postRepository.save(post);
        LoggerUtility.v(DEBUG_TAG, String.format("updatedPost: [%s]", updatedPost));
        return PostMapper.toResponse(updatedPost, commentService.getComments(postId, 0), authUser.getId());
    }
}
