package com.fileuploader.r34dy.service.utils;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Utils {

    public String getMimeType(File file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file);
    }
}
