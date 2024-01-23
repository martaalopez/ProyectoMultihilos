package com.example.proyectomultihilos.model.DAO;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Chronometer implements Runnable {
    // Objeto de bloqueo para sincronización multihilo
    private static final Object lock = new Object();
    // Contador de hilos en ejecución
    private static int runningThreads = 0;

    // Indica si el cronómetro está en ejecución
    private boolean isRunning;
    // Tiempo de inicio del cronómetro
    private long startTime;
    // Tiempo transcurrido desde el inicio
    private long elapsedTime;
    // Etiqueta de la interfaz de usuario para mostrar el tiempo
    private Label label;

    // Constructor que recibe la etiqueta de la interfaz de usuario
    public Chronometer(Label label) {
        this.label = label;
    }

    // Método para iniciar el cronómetro
    public void start() {
        // Sincronización para incrementar el contador de hilos
        synchronized (lock) {
            runningThreads++;
        }

        // Inicialización de variables y creación de un nuevo hilo
        isRunning = true;
        startTime = System.currentTimeMillis();

        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    // Método para detener el cronómetro
    public void stop() {
        // Detiene el cronómetro y calcula el tiempo transcurrido
        isRunning = false;
        elapsedTime = System.currentTimeMillis() - startTime;
        // Actualiza la etiqueta en la interfaz de usuario
        updateLabel();

        // Sincronización para decrementar el contador de hilos
        synchronized (lock) {
            runningThreads--;

        }
    }

    // Método para obtener el tiempo transcurrido formateado
    public String getElapsedTime() {
        long elapsedSeconds = elapsedTime / 1000;
        long days = elapsedSeconds / (24 * 3600);
        long hours = (elapsedSeconds % (24 * 3600)) / 3600;
        long minutes = ((elapsedSeconds % (24 * 3600)) % 3600) / 60;

        return days + " días, " + hours + " horas y " + minutes + " minutos";
    }

    // Método para actualizar la etiqueta en la interfaz de usuario
    private void updateLabel() {
        // Asegura que la actualización se realice en el hilo de la interfaz de usuario
        Platform.runLater(() -> {
            if (label != null) {
                label.setText("Tiempo de préstamo: " + getElapsedTime());
            }
        });
    }

    // Implementación del método run() de la interfaz Runnable
    @Override
    public void run() {
        // Bucle principal del hilo que actualiza continuamente el tiempo
        while (isRunning) {
            elapsedTime = System.currentTimeMillis() - startTime;
            // Actualiza la etiqueta en la interfaz de usuario
            updateLabel();

            try {
                // Pausa el hilo durante 1 segundo
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
