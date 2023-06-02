package com.golinks.hackday.github_lookup.controller;

import com.golinks.hackday.github_lookup.model.GitHubUser;
import com.golinks.hackday.github_lookup.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GitController {

    @Autowired
    GitService gitService;


    @GetMapping("/GitUser/")
    public GitHubUser getUser(@RequestParam String user, @RequestParam(required = false) Optional<Boolean> forked) throws Exception {

        if (forked.isEmpty()) {
            forked = Optional.of(true);
        }
        return gitService.getUser(user, forked.get());
    }

}
