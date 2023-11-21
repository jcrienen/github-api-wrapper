package com.juulcrienen.githubapiwrapper;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

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

}
