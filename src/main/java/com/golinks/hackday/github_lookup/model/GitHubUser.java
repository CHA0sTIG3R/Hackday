package com.golinks.hackday.github_lookup.model;

import java.util.List;

public class GitHubUser {

    private String login;
    private int repos;
    private int stargazers;
    private int forkCount;

    private String avgSize;

    private List<String> languages;

    public GitHubUser(String login, int repos, int stargazers, int forks, int size, List<String> languages) {
        this.login = login;
        this.repos = repos;
        this.stargazers = stargazers;
        this.forkCount = forks;
        this.avgSize = size + " KB";
        this.languages = languages;
    }

    public String getAvgSize() {
        return avgSize;
    }

    public String getLogin() {
        return login;
    }

    public int getRepos() {
        return repos;
    }

    public int getStargazers() {
        return stargazers;
    }

    public int getForkCount() {
        return forkCount;
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
                ", forks=" + forkCount +
                ", languages=" + languages +
                '}';
    }

    public String toMarkdown() {
        return "| " + login + " | " + repos + " | " + stargazers + " | " + forkCount + " | " + languages + " |";
    }

    public String toHtml() {
        return "<tr><td>" + login + "</td><td>" + repos + "</td><td>" + stargazers + "</td><td>" + forkCount + "</td><td>" + languages + "</td></tr>";
    }

    public String toCsv() {
        return login + "," + repos + "," + stargazers + "," + forkCount + "," + languages;
    }

}
