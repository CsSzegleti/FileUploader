package com.fileuploader.r34dy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class FileService {

    @Value("${file.tmp.folder-uri}")
    private String tmpFolderUri;

    @Value("${file.final.folder-uri}")
    private String finalFolderUri;

    public String saveToTmpLocation(MultipartFile file) throws IOException {
        File f = new File(tmpFolderUri + file.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(f);
        outputStream.write(file.getBytes());
        outputStream.close();

        return f.getName();
    }

    public void checkfile(String fileName) {
        /// TODO: check
        // calls private check steps
    }

    public boolean saveToPermanentLocation(String fileName) {
        File f = new File(tmpFolderUri + fileName);
        return f.renameTo(new File(finalFolderUri + fileName));
    }
}
