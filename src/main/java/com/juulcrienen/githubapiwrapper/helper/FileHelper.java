package com.juulcrienen.githubapiwrapper.helper;

import com.juulcrienen.githubapiwrapper.GitHubAPIWrapper;
import org.eclipse.jgit.api.Git;
import org.kohsuke.github.GHRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {

    public static List<File> getFiles(GHRepository repository, String extension) throws Exception {
        return getFiles(repository, repository.getDefaultBranch(), extension);
    }

    public static List<File> getFiles(GHRepository repository, String branch, String extension) throws Exception {
        Path tempRepository = GitHubAPIWrapper.getTemporaryFileHandler().createTempDir(repository.getName());

        Git git = Git.cloneRepository()
                .setURI(repository.getHttpTransportUrl())
                .setDirectory(tempRepository.toFile())
                .call();
        git.close();

        List<File> files = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(tempRepository)) {
            files = walk
                    .filter(f -> f.toString().endsWith(extension)).map(x -> x.toFile()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }
}
