package com.fileuploader.r34dy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseFile {
    private String id;
    private String name;
    private String url;
    private String type;
}
