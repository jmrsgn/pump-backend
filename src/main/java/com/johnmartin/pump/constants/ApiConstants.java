package com.johnmartin.pump.constants;

public class ApiConstants {

    public static final String APP_NAME = "Pump API";
    public static final String API_BASE_V1 = "/api/v1";

    public static class Path {
        // Auth
        public static final String API_AUTH = API_BASE_V1 + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";

        // User
        public static final String API_USER = API_BASE_V1 + "/user";
        public static final String PROFILE = "/profile";

        // Post
        public static final String API_POST = API_BASE_V1 + "/post";

        // Comment
        public static final String API_COMMENT = API_POST + Params.POST_ID + "/comment";
    }

    public static class Params {
        public static final String POST_ID = "/{postId}";
    }
}
