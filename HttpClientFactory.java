package com.abhishek.github.api;

import java.net.http.HttpClient;

/**
 * Singleton Factory for HttpClient (to reuse connection).
 */
public class HttpClientFactory {
    private static final HttpClient INSTANCE = HttpClient.newHttpClient();

    private HttpClientFactory() {}

    public static HttpClient getInstance() {
        return INSTANCE;
    }
}

