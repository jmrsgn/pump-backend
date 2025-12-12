package com.johnmartin.pump.dto.request;

import com.johnmartin.pump.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;

public class CreatePostRequest {

    private String title;

    @NotBlank(message = ApiErrorMessages.Post.POST_DESCRIPTION_IS_REQUIRED)
    private String description;

    public CreatePostRequest(String title, String description, String userId) {
        this.title = title;
        this.description = description;
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
}
