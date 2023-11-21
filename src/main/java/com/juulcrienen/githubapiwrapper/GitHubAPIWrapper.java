package com.juulcrienen.githubapiwrapper;

import com.juulcrienen.githubapiwrapper.helper.TemporaryFileHandler;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitHubAPIWrapper {

    private GitHub github;
    private static TemporaryFileHandler temporaryFileHandler = new TemporaryFileHandler("github-api-wrapper");

    public GitHubAPIWrapper() throws IOException {
        github = GitHub.connectAnonymously();
    }

    public GitHubAPIWrapper(String username, String token) throws IOException {
        github = GitHub.connect(username, token);
    }

    public GitHub getGitHub() {
        return github;
    }

    public void setGithub(GitHub github) {
        this.github = github;
    }

    public static TemporaryFileHandler getTemporaryFileHandler() {
        return temporaryFileHandler;
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

    public List<GHRepository> getGitHubRepositories(InputStream inputStream) throws IOException {
        List<String> repositories = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                repositories.add(line);
            }
        }
        return getGitHubRepositories(repositories);
    }

}
