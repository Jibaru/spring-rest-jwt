package com.ignacior.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:messages.properties")
})
public class TodoListApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TodoListApplication.class);
    }

}
