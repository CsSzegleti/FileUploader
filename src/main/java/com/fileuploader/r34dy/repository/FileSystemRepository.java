package com.fileuploader.r34dy.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
@Slf4j
public class FileSystemRepository {
    //@Value("${resources.dir}")
    private final String RESOURCES_DIR = System.getProperty("user.dir") +
            FileSystems.getDefault().getSeparator() + "files" +
            FileSystems.getDefault().getSeparator();

    public String save(byte[] content, String imageName) {
        log.info("Saving file");
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);

        try {
            Files.createDirectories(newFile.getParent());
            Files.write(newFile, content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile
                .toString();
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }

}
