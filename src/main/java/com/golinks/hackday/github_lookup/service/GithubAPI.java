package com.golinks.hackday.github_lookup.service;

import java.net.URI;

public class GithubAPI {
    private String url;
    private String token;

    public GithubAPI(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public URI getUri() {
        return URI.create(this.url);
    }

}
