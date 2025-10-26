package com.johnmartin.pump.constants;

public class ApiConstants {

    public static final String APP_NAME = "Pump API";

    public static class Path {
        public static final String API_BASE = "/api/v1/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
    }

    public static class Status {
        public static int UNAUTHORIZED = 401;
    }
}
