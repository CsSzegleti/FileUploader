package com.fileuploader.r34dy.service;

import java.util.Arrays;
import java.util.List;

public class Allowed {

    public static final List<String> extensions = Arrays.asList(
        "doc",
        "docx",
        "pdf",
        "odt",
        "xls",
        "xlsx",
        "ods",
        "ppt",
        "pptx",
        "txt"
    );

    public static final List<String> mimeTypes = Arrays.asList(
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/pdf",
        "application/vnd.oasis.opendocument.text",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.oasis.opendocument.spreadsheet",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "text/plain"
    );
}
