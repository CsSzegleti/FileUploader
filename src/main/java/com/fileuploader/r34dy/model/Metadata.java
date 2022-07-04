package com.fileuploader.r34dy.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Metadata {
    private HashMap<String, String> data;
}
