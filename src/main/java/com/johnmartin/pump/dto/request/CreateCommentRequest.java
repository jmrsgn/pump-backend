package com.johnmartin.pump.dto.request;

public class CreateCommentRequest {

    private String comment;
    private String userId;

    public CreateCommentRequest(String comment, String userId) {
        this.comment = comment;
        this.userId = userId;
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
}
