package com.juulcrienen.githubapiwrapper.helper;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemporaryFileHandler {

    private Path tempDir;

    public TemporaryFileHandler(String prefix) {
        try {
            tempDir = Files.createTempDirectory("github-api-wrapper");
        } catch (IOException e) {
            e.printStackTrace();
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
            temp = Files.createTempDirectory(tempDir, name);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return temp;
    }
}
