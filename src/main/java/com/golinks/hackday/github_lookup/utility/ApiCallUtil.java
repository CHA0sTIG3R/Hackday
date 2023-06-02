package com.golinks.hackday.github_lookup.utility;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiCallUtil {

    private static final String TOKEN = System.getenv("GITHUB_TOKEN");
    private static  String url = "https://api.github.com/users/";

    public static List<Map> UserUtil(String user) throws IOException, InterruptedException {

        url = url+user+"/repos";
        var updatedUrl = url;

        var repos = new ArrayList<Map>();
        int pg = 1;
        while (true){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getUri(updatedUrl))
                    .header("Authorization", "token " + TOKEN)
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
            updatedUrl = url + "?page=" + pg;
        }

        return repos;
    }

    private static URI getUri(String s) {
        return URI.create(s);
    }
}
