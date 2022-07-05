package com.fileuploader.r34dy.service;

import com.fileuploader.r34dy.service.exception.SecurityConstraintException;
import com.fileuploader.r34dy.service.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileSecurityService {

    private final Utils utils;

    public boolean checkFile(File fileToCheck) throws SecurityConstraintException, IOException {
        validateExtension(fileToCheck);
        validateContentType(fileToCheck);
        return true;
    }

    private void validateExtension(File fileToCheck) throws SecurityConstraintException {
        if (fileToCheck != null) {
            String extension = FilenameUtils.getExtension(fileToCheck.getName());
            if (!Allowed.extensions.contains(extension.toLowerCase())) {
                throw new SecurityConstraintException("error.file.security.extension.not.allowed");
            }
        }
    }

    private void validateContentType(File fileToCheck) throws IOException, SecurityConstraintException {
        String mimeType = utils.getMimeType(fileToCheck);

        if (!Allowed.mimeTypes.contains(mimeType.toLowerCase())) {
            throw new SecurityConstraintException("error.file.security.mimetype.not.allowed");
        }
    }
}
