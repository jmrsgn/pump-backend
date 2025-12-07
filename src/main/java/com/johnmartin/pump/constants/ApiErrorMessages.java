package com.johnmartin.pump.constants;

public class ApiErrorMessages {

    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String INVALID_ARGUMENT = "Invalid argument";
    public static final String LOGIN_FAILED_PLEASE_TRY_AGAIN_LATER = "Login failed. Please try again later";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String NOT_FOUND = "Not found";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String CONFLICT = "Conflict";

    public static class User {
        public static final String INVALID_CREDENTIALS = "Invalid Credentials";
        public static final String EMAIL_AND_PASSWORD_ARE_REQUIRED = "Email and password are required";
        public static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
        public static final String USER_WITH_EMAIL_NOT_FOUND = "User with email %s not found";
        public static final String USER_WITH_ID_NOT_FOUND = "User with ID %s not found";
        public static final String USER_REGISTRATION_FAILED = "User registration failed";
        public static final String USER_IS_NOT_AUTHENTICATED = "User is not authenticated";
        public static final String USER_NOT_FOUND = "User not found";
    }

    public static class Post {
        public static final String NO_POSTS_FOUND = "No posts found";
        public static final String THERE_ARE_NO_POST_AVAILABLE = "There are no posts available.";
        public static final String THERE_ARE_NO_COMMENTS_AVAILABLE = "There are no comments available.";
        public static final String POST_NOT_FOUND = "Post not found";
    }

}
