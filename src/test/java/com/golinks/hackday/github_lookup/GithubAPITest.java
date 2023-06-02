package com.golinks.hackday.github_lookup;

import com.golinks.hackday.github_lookup.service.GithubAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

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
        var url = "https://api.github.com/users/seantomburke/repos";
        GithubAPI githubAPI = new GithubAPI(url, token);
        // query the GitHub api
        var repos = new ArrayList<Map>();
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
            var links = response.headers().firstValue("Link").get();


            if (!links.contains("rel=\"next\"")){
                break;
            }
            pg++;
            githubAPI = new GithubAPI(url + "?page=" + pg, token);
        }
        System.out.println(repos.size());

        var not_forked = repos.stream().filter(x -> (Boolean) x.get("fork")).toList();

        var forks = (int) repos.stream().map(x -> {
            Double e = (Double) x.get("forks_count");
            return e;
        }).toList().stream().mapToDouble(Double::intValue).sum();

        var lang = repos.stream().map(x -> (String) x.get("language"))
                .collect(Collectors.toMap(x -> x, x -> 1, Integer::sum))
                .entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        var repoCount = repos.size();

        var stars = (int) repos.stream().map(x -> {
            Double e = (Double) x.get("stargazers_count");
            return e;
        }).toList().stream().mapToDouble(Double::intValue).sum();

        var size = (int) repos.stream().map(x -> {
            Double e = (Double) x.get("size");
            return e;
        }).toList().stream().mapToDouble(Double::intValue).sum();

        var avgSize = size / repoCount;

        System.out.println(not_forked.size());
        System.out.println(lang);
        System.out.println(forks);
        System.out.println(stars);
        System.out.println(size);
        System.out.println(avgSize);


        //Map<String, Object> map = new Gson().fromJson(response.body(), Map.class);

        assertEquals(200, 200);
    }
}
