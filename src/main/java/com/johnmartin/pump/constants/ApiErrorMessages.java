package com.johnmartin.pump.constants;

public class ApiErrorMessages {

    public static final String INTERNAL_SERVER_ERROR = "Internal server error";

    public static class User {
        public static final String INVALID_CREDENTIALS = "Invalid Credentials";
        public static final String EMAIL_AND_PASSWORD_ARE_REQUIRED = "Email and password are required";
        public static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
    }

    public static class Post {
        public static final String NO_POSTS_FOUND = "No posts found";
        public static final String THERE_ARE_NO_POST_AVAILABLE = "There are no posts available.";
    }

}
