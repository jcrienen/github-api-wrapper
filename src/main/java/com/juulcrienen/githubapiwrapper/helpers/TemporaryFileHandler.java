package com.juulcrienen.githubapiwrapper.helpers;

import com.juulcrienen.githubapiwrapper.GitHubAPIWrapper;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemporaryFileHandler {

    private Path tempDir;

    public TemporaryFileHandler(String prefix) {
        try {
            GitHubAPIWrapper.debug("Creating temporary directory " + prefix);
            tempDir = Files.createTempDirectory(prefix);
        } catch (IOException e) {
            GitHubAPIWrapper.error(e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileUtils.deleteQuietly(tempDir.toFile());
        }));
    }

    public Path getTempDir() {
        return tempDir;
    }

    public Path createTempDir(String name) {
        Path temp = null;
        try {
            GitHubAPIWrapper.debug("Creating temporary directory " + name);
            temp = Files.createTempDirectory(tempDir, name);
        } catch (IOException e) {
            GitHubAPIWrapper.error(e.getMessage());
        }

        return temp;
    }
}
