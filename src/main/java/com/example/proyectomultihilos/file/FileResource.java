package com.example.proyectomultihilos.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileResource {
    private String filePath;

    public FileResource(String filePath) {
        this.filePath = filePath;
    }

    public synchronized void write(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
