package com.golinks.hackday.github_lookup.model;

import java.util.List;

public class GitHubUser {

    private String login;
    private int repos;
    private int stargazers;
    private int forks;

    private int size;

    private List<String> languages;

    public GitHubUser(String login, int repos, int stargazers, int forks, int size, List<String> languages) {
        this.login = login;
        this.repos = repos;
        this.stargazers = stargazers;
        this.forks = forks;
        this.size = size;
        this.languages = languages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

    public void setStargazers(int stargazers) {
        this.stargazers = stargazers;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
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

    public int getForks() {
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
