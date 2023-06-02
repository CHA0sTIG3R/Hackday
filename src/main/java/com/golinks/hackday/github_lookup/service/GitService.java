package com.golinks.hackday.github_lookup.service;

import com.golinks.hackday.github_lookup.model.GitHubUser;
import com.golinks.hackday.github_lookup.utility.ApiCallUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GitService {

    public GitHubUser getUser(String user, boolean forked) throws Exception {

        List<Map> userInfo = ApiCallUtil.UserUtil(user);

        var not_forked = userInfo.stream().filter(x -> (Boolean) x.get("fork")).toList();

        if (!forked) {
            userInfo = not_forked;
        }

        return getGitHubUser(user, userInfo);
    }

    private GitHubUser getGitHubUser(String user, List<Map> userInfo) {
        var forks = (int) userInfo.stream().map(x -> (Double) x.get("forks_count")).toList().stream().mapToDouble(Double::intValue).sum();

        var lang = userInfo.stream().map(x -> (String) x.get("language"))
                .collect(Collectors.toMap(x -> x, x -> 1, Integer::sum))
                .entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(x -> x.getKey() + " " + x.getValue())
                .filter(x -> !x.contains("null")).toList();

        var repoCount = userInfo.size();

        var stars = (int) userInfo.stream().map(x -> (Double) x.get("stargazers_count")).toList().stream().mapToDouble(Double::intValue).sum();

        var size = (int) userInfo.stream().map(x -> (Double) x.get("size")).toList().stream().mapToDouble(Double::intValue).sum();

        var avgSize = size / repoCount;

        return new GitHubUser(user, userInfo.size(), stars, forks, avgSize, lang);
    }

}
