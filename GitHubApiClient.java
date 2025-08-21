package com.abhishek.github.api;

import com.abhishek.github.util.RateLimitHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Encapsulates HTTP requests to GitHub API.
 */
public class GitHubApiClient {

    private static final String BASE_URL = "https://api.github.com";
    private final String authToken;
    private final HttpClient client;

    public GitHubApiClient(String authToken) {
        this.authToken = authToken;
        this.client = HttpClientFactory.getInstance();
    }

    public String get(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Authorization", "token " + authToken)
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Handle rate limits gracefully
        RateLimitHandler.checkRateLimit(response);

        if (response.statusCode() != 200) {
            throw new RuntimeException("GitHub API Error [" + response.statusCode() + "] : " + response.body());
        }
        return response.body();
    }
}

