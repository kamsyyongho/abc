package com.skt.doss;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DossIamApiApplication {

    private static final String PROPERTIES =
            "spring.config.location="
                    + "classpath:/config/application/";

    public static void main(String[] args) {

        new SpringApplicationBuilder(DossIamApiApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }

}
