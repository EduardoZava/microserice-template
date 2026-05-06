package com.template.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CatalogoServicoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogoServicoApplication.class, args);
    }
}
