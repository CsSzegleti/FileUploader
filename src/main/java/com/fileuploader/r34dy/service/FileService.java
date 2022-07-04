package com.fileuploader.r34dy.service;

import com.fileuploader.r34dy.model.File;
import com.fileuploader.r34dy.repository.FileRepository;
import com.fileuploader.r34dy.repository.FileSystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final FileSystemRepository fileSystemRepository;

    @Autowired
    public FileService(FileRepository fileRepository, FileSystemRepository fileSystemRepository){
        this.fileRepository = fileRepository;
        this.fileSystemRepository = fileSystemRepository;
    }

    public String store(MultipartFile file) throws Exception {
        String location = fileSystemRepository.save(file.getBytes(), file.getOriginalFilename());
        log.info("Location: " + location);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileToSave  = new File(fileName, file.getContentType(), location);
        return fileRepository.save(fileToSave).getId();
    }

    public FileSystemResource getFile(String id){
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(file.getLocation());
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
