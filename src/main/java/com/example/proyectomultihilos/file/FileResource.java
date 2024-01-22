package com.example.proyectomultihilos.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileResource {
    // Ruta del archivo
    private String filePath;

    // Constructor que recibe la ruta del archivo
    public FileResource(String filePath) {
        this.filePath = filePath;
    }

    // Método sincronizado para escribir en el archivo
    public synchronized void write(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Escribe el contenido y agrega una nueva línea
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            // Maneja posibles errores de entrada/salida
            e.printStackTrace();
        }
    }

    // Método sincronizado para leer todas las líneas del archivo
    public synchronized List<String> readAll() {
        // Lista para almacenar las líneas leídas
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Lee cada línea del archivo y la agrega a la lista
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            // Maneja posibles errores de entrada/salida
            e.printStackTrace();
        }
        // Devuelve la lista de líneas leídas
        return lines;
    }
}
