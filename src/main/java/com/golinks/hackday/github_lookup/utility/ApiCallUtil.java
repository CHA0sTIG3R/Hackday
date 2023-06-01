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

    public static HttpResponse<String> StarredUtil(String user) throws IOException, InterruptedException {
        GithubAPI githubAPI = new GithubAPI("https://api.github.com/users/"+user+"/starred", token);
        // query the GitHub api
        return getStringHttpResponse(githubAPI);
    }

    public static Double ReposUtil(String user) throws IOException, InterruptedException {
        GithubAPI githubAPI = new GithubAPI("https://api.github.com/users/"+user+"/repos", token);
        // query the GitHub api
        var res = getStringHttpResponse(githubAPI);
        List<Map> list = new Gson().fromJson(res.body(), List.class);
        var lst = list.stream().map(x -> {
            Double e = (Double) x.get("forks_count");
            return e;
        }).toList().stream().mapToDouble(Double::intValue).sum();
        return lst;
    }

    public static List langUtil(String user) throws IOException, InterruptedException {
        GithubAPI githubAPI = new GithubAPI("https://api.github.com/users/"+user+"/repos", token);
        // query the GitHub api
        var res = getStringHttpResponse(githubAPI);
        List<Map> list = new Gson().fromJson(res.body(), List.class);
        var lst2 = list.stream().map(x -> x.get("language")).toList();
        return lst2;
    }

    public static Double UserUtil(String user) throws IOException, InterruptedException {
        GithubAPI githubAPI = new GithubAPI("https://api.github.com/users/"+user, token);
        // query the GitHub api
        var res = getStringHttpResponse(githubAPI);
        var userInfo = new Gson().fromJson(res.body(), Map.class);
        return (Double) userInfo.get("public_repos");
    }

    private static HttpResponse<String> getStringHttpResponse(GithubAPI githubAPI) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(githubAPI.getUri())
                .header("Authorization", "token " + githubAPI.getToken())
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }


}
