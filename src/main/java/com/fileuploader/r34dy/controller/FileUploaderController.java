package com.fileuploader.r34dy.controller;

import com.fileuploader.r34dy.model.ResponseFile;
import com.fileuploader.r34dy.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileUploaderController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        String message;
        try {
            log.info("Try to upload...");
            String id = fileService.store(file);
            message = "Uploaded the file: " + file.getOriginalFilename() + " with id " +
            id;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getAllFiles() {
        List<ResponseFile> files = fileService.getAllFiles().map(file -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromUriString(file.getLocation())
                    .toUriString();

            return new ResponseFile(file.getId(),
                    file.getName(),
                    fileDownloadUri,
                    file.getType());
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

//    @GetMapping("/files/{id}")
//    public ResponseEntity<FileSystemResource> getFile(@PathVariable String id){
//        return ResponseEntity.status(HttpStatus.OK).body(fileService.getFile(id));
//    }
    @GetMapping("/files/{id}")
    public URL getFile(@PathVariable String id) throws IOException {
        return fileService.getFile(id).getURL();
}
}
