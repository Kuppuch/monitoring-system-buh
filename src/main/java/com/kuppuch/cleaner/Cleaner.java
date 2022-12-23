package com.kuppuch.cleaner;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Cleaner implements Runnable {

    public void clean() {
        File folder = new File("C:/Users/Kuppuch/IdeaProjects/buh2/uploads");
        File[] listOfFiles = folder.listFiles();

        boolean b = listOfFiles != null;
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }


    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 * 10 * 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clean();

        }
    }
}
