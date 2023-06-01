package com.golinks.hackday.github_lookup.controller;

import com.golinks.hackday.github_lookup.model.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitController {

    @GetMapping("/GitUser/{user}")
    public GitHubUser getUser(@PathVariable String user) throws Exception {
        return new GitHubUser(user);
    }

}
