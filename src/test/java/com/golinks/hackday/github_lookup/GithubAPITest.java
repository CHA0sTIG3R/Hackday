package com.golinks.hackday.github_lookup;

import com.golinks.hackday.github_lookup.service.GitService;
import com.golinks.hackday.github_lookup.utility.ApiCallUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GithubAPITest {

    @Test
    public void testApiCall() throws IOException, InterruptedException {

        var repos = ApiCallUtil.UserUtil("CHA0sTIG3R");

        System.out.println(repos.size());

        var not_forked = repos.stream().filter(x -> (Boolean) x.get("fork")).toList();

        var forks = (int) repos.stream().map(x -> (Double) x.get("forks_count")).toList().stream().mapToDouble(Double::intValue).sum();

        var lang = repos.stream().map(x -> (String) x.get("language"))
                .collect(Collectors.toMap(x -> x, x -> 1, Integer::sum))
                .entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(x -> x.getKey() + " " + x.getValue())
                .filter(x -> !x.contains("null"))
                .collect(Collectors.toList());

        var repoCount = repos.size();

        var stars = (int) repos.stream().map(x -> (Double) x.get("stargazers_count")).toList().stream().mapToDouble(Double::intValue).sum();

        var size = (int) repos.stream().map(x -> (Double) x.get("size")).toList().stream().mapToDouble(Double::intValue).sum();

        var avgSize = size / repoCount;

        System.out.println(not_forked.size());
        System.out.println(lang);
        System.out.println(forks);
        System.out.println(stars);
        System.out.println(size);
        System.out.println(avgSize);

        assertTrue(repos.size() > 0);
    }

    @Test
    public void testGitService() throws Exception {
        var gitService = new GitService();
        var gitHubUser = gitService.getUser("CHA0sTIG3R", true);
        System.out.println(gitHubUser);



        assertTrue(gitHubUser.getRepos() > 0);
    }

}
