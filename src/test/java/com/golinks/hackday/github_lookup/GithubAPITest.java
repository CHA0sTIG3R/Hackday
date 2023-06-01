package com.golinks.hackday.github_lookup;

import com.golinks.hackday.github_lookup.service.GithubAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GithubAPITest {
    @Test
    public void testGetUrl() {
        GithubAPI githubAPI = new GithubAPI("https://api.github.com", "1234");
        assertEquals("https://api.github.com", githubAPI.getUrl());
    }

    @Test
    public void testGetToken() {
        GithubAPI githubAPI = new GithubAPI("https://api.github.com", "1234");
        assertEquals("1234", githubAPI.getToken());
    }

    @Test
    public void testcallURL() throws IOException, InterruptedException {
        var token = System.getenv("GITHUB_TOKEN");
        var url = "https://api.github.com/search/seantomburke/repos";
        GithubAPI githubAPI = new GithubAPI("https://api.github.com/search/seantomburke/repos", token);
        // query the GitHub api
        var repos = new ArrayList<>();
        int pg = 1;
        while (true){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(githubAPI.getUri())
                    .header("Authorization", "token " + githubAPI.getToken())
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Map> list = new Gson().fromJson(response.body(), List.class);
            repos.addAll(list);
            if (list.size() < 100){
                break;
            }
            pg++;
            githubAPI = new GithubAPI(url + "?page=" + pg, token);
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(githubAPI.getUri())
                .header("Authorization", "token " + githubAPI.getToken())
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Map> list = new Gson().fromJson(response.body(), List.class);
//        System.out.println(response.body());
//        System.out.println(response.body().length());
        //System.out.println(Arrays.toString(list));
        var lst = list.stream().map(x -> {
            Double e = (Double) x.get("forks_count");
            return e;
        }).toList().stream().mapToDouble(Double::intValue).sum();
        System.out.println(lst);


        //Map<String, Object> map = new Gson().fromJson(response.body(), Map.class);

        assertEquals(200, response.statusCode());
    }
}
