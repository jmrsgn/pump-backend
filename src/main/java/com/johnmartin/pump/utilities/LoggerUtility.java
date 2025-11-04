package com.johnmartin.pump.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtility {

    private LoggerUtility() {
    }

    private static Logger getLogger(String tag) {
        return LoggerFactory.getLogger(tag);
    }

    public static void info(String tag, String message, Object... args) {
        getLogger(tag).info(message, args);
    }

    public static void debug(String tag, String message, Object... args) {
        getLogger(tag).debug(message, args);
    }

    public static void warn(String tag, String message, Object... args) {
        getLogger(tag).warn(message, args);
    }

    public static void error(String tag, String message, Throwable throwable) {
        getLogger(tag).error(message, throwable);
    }
}
