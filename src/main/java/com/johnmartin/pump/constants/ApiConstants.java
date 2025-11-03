package com.johnmartin.pump.constants;

public class ApiConstants {

    public static final String APP_NAME = "Pump API";

    public static class Path {
        // Auth
        public static final String API_BASE = "/api/v1/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";

        // User
        public static final String API_USER = "/api/v1/user";
        public static final String PROFILE = "/profile";
    }

    public static class Status {
        public static int UNAUTHORIZED = 401;
    }

    public static class Params {
        public static final String EMAIL = "/{email}";
    }
}
