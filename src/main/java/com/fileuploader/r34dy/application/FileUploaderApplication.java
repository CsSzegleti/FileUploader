package com.fileuploader.r34dy.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fileuploader.r34dy.controller", "com.fileuploader.r34dy.service", "com.fileuploader.r34dy.repository"})
@EnableJpaRepositories(basePackages = "com.fileuploader.r34dy.repository")
@EntityScan("com.fileuploader.r34dy.model")
public class FileUploaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploaderApplication.class, args);
    }

}
