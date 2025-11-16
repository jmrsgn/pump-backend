package com.johnmartin.pump.dto.response;

import java.time.Instant;

public class CommentResponse {
    private String id;
    private String comment;

    private String userId;
    private String postId;

    // Store user basic info for faster frontend rendering
    private String userName;
    private String userProfileImageUrl;

    private Instant createdAt;
    private Instant updatedAt;

    private int likesCount;
    private int repliesCount;

    public CommentResponse(String id,
                           String comment,
                           String userId,
                           String postId,
                           String userName,
                           String userProfileImageUrl,
                           Instant createdAt,
                           Instant updatedAt,
                           int likesCount,
                           int repliesCount) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.postId = postId;
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likesCount = likesCount;
        this.repliesCount = repliesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    @Override
    public String toString() {
        return "CommentResponse{" + "id='" + id + '\'' + ", comment='" + comment + '\'' + ", userId='" + userId + '\''
               + ", postId='" + postId + '\'' + ", userName='" + userName + '\'' + ", userProfileImageUrl='"
               + userProfileImageUrl + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", likesCount="
               + likesCount + ", repliesCount=" + repliesCount + '}';
    }
}
