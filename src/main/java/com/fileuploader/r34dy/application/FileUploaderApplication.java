package com.fileuploader.r34dy.application;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {
        "com.fileuploader.r34dy.resource",
        "com.fileuploader.r34dy.service"})
public class FileUploaderApplication {

    @Value("${spring.rabbitmq.queue-name}")
    private String queueName;

    @Value("${spring.rabbitmq.exchange-name}")
    private String exchangeName;

    public static void main(String[] args) {
        SpringApplication.run(FileUploaderApplication.class, args);
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }
}
