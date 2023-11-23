package com.juulcrienen.githubapiwrapper.helpers;

import com.juulcrienen.githubapiwrapper.GitHubAPIWrapper;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jgit.api.Git;
import org.kohsuke.github.GHRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {

    public static List<File> getFiles(GHRepository repository, String extension) throws Exception {
        return getFiles(repository, repository.getDefaultBranch(), extension);
    }

    public static List<File> getFiles(GHRepository repository, String branch, String extension) throws Exception {
        Path tempRepository = GitHubAPIWrapper.getTemporaryFileHandler().createTempDir(repository.getName());

        String branchFull = "refs/heads/" + branch;

        Git git = Git.cloneRepository()
                .setURI(repository.getHttpTransportUrl())
                .setDirectory(tempRepository.toFile())
                .setBranchesToClone(Arrays.asList(branchFull))
                .setBranch(branchFull)
                .call();
        git.close();

        List<File> files = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(tempRepository)) {
            files = walk
                    .filter(f -> FilenameUtils.getExtension(f.toString()).equals(extension)).map(x -> x.toFile()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }
}
