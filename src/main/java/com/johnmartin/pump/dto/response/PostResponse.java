package com.johnmartin.pump.dto.response;

import java.time.Instant;
import java.util.List;

import com.johnmartin.pump.entities.PostEntity;

public class PostResponse {
    private String id;
    private String title;
    private String description;

    private String userId;

    // Store user basic info for faster frontend rendering
    private String userName;
    private String userProfileImageUrl;

    private Instant createdAt;
    private Instant updatedAt;

    private int likesCount;
    private List<PostEntity.Comment> comments;
    private int commentsCount;

    public PostResponse(String id,
                        String title,
                        String description,
                        String userId,
                        String userName,
                        String userProfileImageUrl,
                        Instant createdAt,
                        Instant updatedAt,
                        int likesCount,
                        List<PostEntity.Comment> comments,
                        int commentsCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likesCount = likesCount;
        this.comments = comments;
        this.commentsCount = commentsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<PostEntity.Comment> getComments() {
        return comments;
    }

    public void setComments(List<PostEntity.Comment> comments) {
        this.comments = comments;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public static class CommentResponse {
        private String userName;
        private String userProfileImageUrl;
        private String comment;
        private int likesCount;

        public CommentResponse() {
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserProfileImageUrl() {
            return userProfileImageUrl;
        }

        public void setUserProfileImageUrl(String userProfileImageUrl) {
            this.userProfileImageUrl = userProfileImageUrl;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(int likesCount) {
            this.likesCount = likesCount;
        }

        @Override
        public String toString() {
            return "CommentResponse{" + "userName='" + userName + '\'' + ", userProfileImageUrl='" + userProfileImageUrl
                   + '\'' + ", comment='" + comment + '\'' + ", likesCount=" + likesCount + '}';
        }
    }

    @Override
    public String toString() {
        return "PostResponse{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description
               + '\'' + ", userId='" + userId + '\'' + ", userName='" + userName + '\'' + ", userProfileImageUrl='"
               + userProfileImageUrl + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", likesCount="
               + likesCount + ", comments=" + comments + ", commentsCount=" + commentsCount + '}';
    }
}
