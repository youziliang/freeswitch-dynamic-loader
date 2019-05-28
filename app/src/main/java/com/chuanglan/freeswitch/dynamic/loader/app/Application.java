package com.chuanglan.freeswitch.dynamic.loader.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "com.chuanglan.freeswitch.dynamic.loader.*")
@ImportResource(locations = {"classpath*:spring/app-context.xml"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
