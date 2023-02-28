package com.wp;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@Log4j
public class WesttwoMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(WesttwoMusicApplication.class, args);
    }

}
