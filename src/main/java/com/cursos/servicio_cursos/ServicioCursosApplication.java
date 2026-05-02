package com.cursos.servicio_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServicioCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioCursosApplication.class, args);
    }

}