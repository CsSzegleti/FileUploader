package com.fileuploader.r34dy.resource;

import com.fileuploader.r34dy.service.FileSecurityService;
import com.fileuploader.r34dy.service.FileHandlerService;
import com.fileuploader.r34dy.service.exception.SecurityConstraintException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FileUploaderController {

    @Value("${spring.rabbitmq.queue-name}")
    private String queueName;

    @Value("${file.tmp.folder-uri}")
    private String tmpFolderName;

    private final FileHandlerService fileHandlerService;

    private final FileSecurityService fileSecurityService;

    private final RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/upload", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpStatus uploadFile(@RequestPart("file") MultipartFile file,
                                 @RequestPart("metadata") Map<String,String> metadata) throws IOException, SecurityConstraintException {
        String fileName = fileHandlerService.saveToTmpLocation(file);

        fileSecurityService.checkFile(new File(URI.create(String.format("%s%s",tmpFolderName, fileName))));

        fileHandlerService.moveToPermanentLocation(fileName);

        fileHandlerService.additionalData(metadata, file);

        rabbitTemplate.convertAndSend(queueName, metadata);

        return HttpStatus.OK;
    }
}
