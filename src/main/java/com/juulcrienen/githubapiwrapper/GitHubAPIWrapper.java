package com.juulcrienen.githubapiwrapper;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitHubAPIWrapper {

    private GitHub github;

    public GitHubAPIWrapper() throws IOException {
        github = GitHub.connect();
    }

    public GitHub getGitHub() {
        return github;
    }

    public GHRepository getGitHubRepository(String repository) throws IOException {
        return github.getRepository(repository);
    }

    public List<GHRepository> getGitHubRepositories(List<String> repositories) throws IOException{
        List<GHRepository> result = new ArrayList<>();
        for(String repo : repositories) {
            result.add(github.getRepository(repo));
        }
        return result;
    }

}
