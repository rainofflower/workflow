package com.rainofflower.learn.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author YangHui
 * @date 2021-03-29
 */
@EnableSwagger2
@SpringBootApplication
public class ActApp {

    public static void main(String... args){
        SpringApplication.run(ActApp.class,args);
    }
}
