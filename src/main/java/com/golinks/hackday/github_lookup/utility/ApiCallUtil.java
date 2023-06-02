package com.golinks.hackday.github_lookup.utility;

import com.golinks.hackday.github_lookup.service.GithubAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ApiCallUtil {

    private static final String token = System.getenv("GITHUB_TOKEN");
    private static final String url = "https://api.github.com/users/";

    public static HttpResponse<String> UserUtil(String user) throws IOException, InterruptedException {
        GithubAPI githubAPI = new GithubAPI(url+user+"/repos", token);
        return getStringHttpResponse(githubAPI);
    }

    private static HttpResponse<String> getStringHttpResponse(GithubAPI githubAPI) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(githubAPI.getUri())
                .header("Authorization", "token " + githubAPI.getToken())
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }


}
