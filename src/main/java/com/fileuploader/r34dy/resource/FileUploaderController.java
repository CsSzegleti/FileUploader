package com.fileuploader.r34dy.resource;

import com.fileuploader.r34dy.service.FileService;
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

import java.io.IOException;
import java.util.HashMap;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FileUploaderController {

    @Value("${spring.rabbitmq.queue-name}")
    private String queueName;

    private final FileService fileService;

    private final RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/upload", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpStatus uploadFile(@RequestPart("file") MultipartFile file,
                                 @RequestPart("metadata") HashMap<String,String> metadata) throws IOException {
        String fileName = fileService.saveToTmpLocation(file);

        fileService.checkFile(fileName);

        fileService.moveToPermanentLocation(fileName);

        rabbitTemplate.convertAndSend(queueName, metadata);

        return HttpStatus.OK;
    }
}
