package com.kuppuch;

import com.kuppuch.cleaner.Cleaner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        Cleaner cleaner = new Cleaner();
        Thread clean = new Thread(cleaner);
        clean.start();
    }

}