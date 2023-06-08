package io.zestic.retrofit.interceptor;


public enum LogStrategy {

    /**
     * No config
     */
    NULL,

    /**
     * No logs.
     */
    NONE,

    /**
     * Logs request and response lines.
     */
    BASIC,

    /**
     * Logs request and response lines and their respective headers.
     */
    HEADERS,

    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     */
    BODY
}