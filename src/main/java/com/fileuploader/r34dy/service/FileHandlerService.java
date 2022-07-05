package com.fileuploader.r34dy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
@Slf4j
public class FileHandlerService {

    @Value("${file.tmp.folder-uri}")
    private String tmpFolderUri;

    @Value("${file.final.folder-uri}")
    private String finalFolderUri;

    public String saveToTmpLocation(MultipartFile file) throws IOException {
        File f = new File(URI.create(UriEncoder.encode(
                tmpFolderUri + createUniqueFileName(FilenameUtils
                        .getExtension(file.getOriginalFilename())))));
        FileOutputStream outputStream = new FileOutputStream(f);
        outputStream.write(file.getBytes());
        outputStream.close();

        return f.getName();
    }

    public boolean moveToPermanentLocation(String fileName) {
        File f = new File(URI.create(UriEncoder.encode(tmpFolderUri + fileName)));
        return f.renameTo(new File(URI.create(UriEncoder.encode(finalFolderUri + fileName))));
    }

    private String createUniqueFileName(String extension) {
        return String.format("%s.%s",UUID.randomUUID(), extension);
    }


}
