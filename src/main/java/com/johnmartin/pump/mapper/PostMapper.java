package com.johnmartin.pump.mapper;

import java.util.List;

import com.johnmartin.pump.dto.response.CommentResponse;
import com.johnmartin.pump.dto.response.PostResponse;
import com.johnmartin.pump.entities.PostEntity;

public class PostMapper {
    public static PostResponse toResponse(PostEntity postEntity, List<CommentResponse> comments) {
        return new PostResponse(postEntity.getId(),
                                postEntity.getTitle(),
                                postEntity.getDescription(),
                                postEntity.getUserId(),
                                postEntity.getUserName(),
                                postEntity.getUserProfileImageUrl(),
                                postEntity.getCreatedAt(),
                                postEntity.getUpdatedAt(),
                                comments,
                                postEntity.getLikesCount(),
                                postEntity.getCommentsCount(),
                                postEntity.getSharesCount(),
                                postEntity.getLikedUserIds());
    }
}
