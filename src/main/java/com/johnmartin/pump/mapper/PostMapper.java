package com.johnmartin.pump.mapper;

import java.util.List;

import com.johnmartin.pump.dto.response.CommentResponse;
import com.johnmartin.pump.dto.response.PostResponse;
import com.johnmartin.pump.entities.PostEntity;

public class PostMapper {
    public static PostResponse toResponse(PostEntity post, List<CommentResponse> comments, String currentUserId) {

        boolean isLiked = post.getLikedUserIds() != null && post.getLikedUserIds().contains(currentUserId);

        return new PostResponse(post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                post.getUserId(),
                                post.getUserName(),
                                post.getUserProfileImageUrl(),
                                post.getCreatedAt(),
                                post.getUpdatedAt(),
                                comments,
                                post.getLikesCount(),
                                post.getCommentsCount(),
                                post.getSharesCount(),
                                post.getLikedUserIds(),
                                isLiked);
    }
}
