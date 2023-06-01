package com.golinks.hackday.github_lookup.model;

import com.golinks.hackday.github_lookup.utility.ApiCallUtil;

import java.io.IOException;
import java.util.List;

public class GitHubUser {

    private String login;
    private Double repos;
    private int stargazers;
    private Double forks;
    private List<String> languages;

    public GitHubUser(String login) throws IOException, InterruptedException {
        var user = ApiCallUtil.UserUtil(login);
        var userRepos = ApiCallUtil.ReposUtil(login);
        var userLang = ApiCallUtil.langUtil(login);

        this.login = login;
        this.repos = user;
        this.stargazers = 0;
        this.forks = userRepos;
        this.languages = userLang;
    }

    public String getLogin() {
        return login;
    }

    public Double getRepos() {
        return repos;
    }

    public int getStargazers() {
        return stargazers;
    }

    public Double getForks() {
        return forks;
    }

    public List<String> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "login='" + login + '\'' +
                ", repos=" + repos +
                ", stargazers=" + stargazers +
                ", forks=" + forks +
                ", languages=" + languages +
                '}';
    }

    public String toMarkdown() {
        return "| " + login + " | " + repos + " | " + stargazers + " | " + forks + " | " + languages + " |";
    }

    public String toHtml() {
        return "<tr><td>" + login + "</td><td>" + repos + "</td><td>" + stargazers + "</td><td>" + forks + "</td><td>" + languages + "</td></tr>";
    }

    public String toCsv() {
        return login + "," + repos + "," + stargazers + "," + forks + "," + languages;
    }

}
