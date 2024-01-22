package com.example.proyectomultihilos.model.DAO;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Chronometer implements Runnable {
    private static final Object lock = new Object();
    private static int runningThreads = 0;

    private boolean isRunning;
    private long startTime;
    private long elapsedTime;
    private Label label;

    public Chronometer(Label label) {
        this.label = label;
    }

    public void start() {
        synchronized (lock) {
            runningThreads++;
        }

        isRunning = true;
        startTime = System.currentTimeMillis();

        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        isRunning = false;
        elapsedTime = System.currentTimeMillis() - startTime;
        updateLabel();

        synchronized (lock) {
            runningThreads--;
            if (runningThreads == 0) {
            }
        }
    }

    public String getElapsedTime() {
        long elapsedSeconds = elapsedTime / 1000;
        long days = elapsedSeconds / (24 * 3600);
        long hours = (elapsedSeconds % (24 * 3600)) / 3600;
        long minutes = ((elapsedSeconds % (24 * 3600)) % 3600) / 60;

        return days + " días, " + hours + " horas y " + minutes + " minutos";
    }

    private void updateLabel() {
        Platform.runLater(() -> {
            if (label != null) {
                label.setText("Tiempo de préstamo: " + getElapsedTime());
            }
        });
    }

    @Override
    public void run() {
        while (isRunning) {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateLabel();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}