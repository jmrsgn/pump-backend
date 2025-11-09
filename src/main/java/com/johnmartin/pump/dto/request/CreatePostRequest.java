package com.johnmartin.pump.dto.request;

import java.time.Instant;

public class CreatePostRequest {

    private String title;
    private String description;
    private String userId;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String description, String userId, Instant createdAt, Instant updatedAt) {
        this.title = title;
        this.description = description;
        this.userId = userId;
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
}
