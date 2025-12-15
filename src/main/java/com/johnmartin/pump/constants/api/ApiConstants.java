package com.johnmartin.pump.constants.api;

public class ApiConstants {

    public static final String APP_NAME = "Pump API";
    public static final String API_BASE_V1 = "/api/v1";

    public static class Path {
        public static final String HEALTH = "/health";

        // Auth
        public static final String API_AUTH = API_BASE_V1 + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";

        // User
        public static final String API_USER = API_BASE_V1 + "/user";
        public static final String PROFILE = "/profile";

        // Post
        public static final String API_POST = API_BASE_V1 + "/post";
        public static final String POST_INFO = "/{postId}";
        public static final String POST_LIKE = POST_INFO + "/like";

        // Comment
        public static final String API_COMMENT = API_POST + "/{postId}/comment";
        public static final String COMMENT_INFO = "/{commentId}";
    }

    public static class Params {
        public static final String POST_ID = "postId";
        public static final String COMMENT_ID = "commentId";
    }

    public static class Error {
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String NOT_FOUND = "Not found";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String CONFLICT = "Conflict";
        public static final String FORBIDDEN = "Forbidden";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }
}
