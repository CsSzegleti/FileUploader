package com.fileuploader.r34dy.resource;

import com.fileuploader.r34dy.model.Metadata;
import com.fileuploader.r34dy.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FileUploaderController {

    private final FileService fileService;

    @PostMapping("/upload")
    public HttpStatus uploadFile(@RequestPart("file") MultipartFile file,
                                 @RequestPart("metadata") Metadata metadata) throws IOException {
        // save to temp folder
        String fileName = fileService.saveToTmpLocation(file);

        // perform check
        fileService.checkfile(fileName);

        // save to permanent location
        fileService.saveToPermanentLocation(fileName);

        // rabbit MQ


        return HttpStatus.OK;
    }
}
