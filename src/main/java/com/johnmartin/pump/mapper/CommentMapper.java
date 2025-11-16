package com.johnmartin.pump.mapper;

import com.johnmartin.pump.dto.response.CommentResponse;
import com.johnmartin.pump.entities.CommentEntity;

public class CommentMapper {

    public static CommentResponse toResponse(CommentEntity commentEntity) {
        return new CommentResponse(commentEntity.getId(),
                                   commentEntity.getComment(),
                                   commentEntity.getUserId(),
                                   commentEntity.getPostId(),
                                   commentEntity.getUserName(),
                                   commentEntity.getUserProfileImageUrl(),
                                   commentEntity.getCreatedAt(),
                                   commentEntity.getUpdatedAt(),
                                   commentEntity.getLikesCount(),
                                   commentEntity.getRepliesCount());
    }
}
