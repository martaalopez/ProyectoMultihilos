package com.example.proyectomultihilos.model.DAO;

import com.example.proyectomultihilos.file.FileResource;
import com.example.proyectomultihilos.model.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    // Lista de libros en la biblioteca
    private List<Book> books;
    // Historial de transacciones
    private List<String> history;
    // Archivo de historial
    private FileResource historyFile;

    // Constructor que inicializa la lista de libros, historial y el archivo de historial
    public BookDAO() {
        this.books = new ArrayList<>();
        this.history = new ArrayList<>();
        this.historyFile = new FileResource("C:/Users/Usuario/Desktop/history.txt");
    }

    // Método para obtener una copia de la lista de historial
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    // Método sincronizado para agregar un libro a la lista de libros
    public synchronized void addBook(Book book) {
        books.add(book);
        System.out.println("Libro agregado: " + book.getName());
        // Notificar a los posibles hilos en espera
        notify();
    }

    // Método sincronizado para prestar un libro
    public synchronized void borrowBook(Book selectedBook) {
        if (selectedBook.isAvailable()) {
            selectedBook.setAvailable(false);
            historyFile.write("Libro prestado: " + selectedBook.getName());
            System.out.println("Libro prestado: " + selectedBook.getName());
        } else {
            System.out.println("El libro ya está prestado.");
        }
    }

    // Método sincronizado para devolver un libro
    public synchronized void returnBook(Book selectedBook) {
        selectedBook.setAvailable(true);
        historyFile.write("Libro devuelto: " + selectedBook.getName());
        System.out.println("Libro devuelto: " + selectedBook.getName());
        // Agregar el libro devuelto nuevamente a la lista de libros
        books.add(selectedBook);
        // Notificar a los posibles hilos en espera
        notify();
    }

    // Método para actualizar la información de todos los libros en paralelo
    public void updateBooksInfo() {
        List<Book> bookList = new ArrayList<>(books);

        List<Thread> threads = new ArrayList<>();
        for (Book book : bookList) {
            Thread thread = new Thread(() -> {
                synchronized (book) {
                    // Modificar el nombre del libro y registrar la actualización en el historial
                    book.setName(book.getName() + " - Actualizado");
                    history.add("Información actualizada para: " + book.getName());
                    System.out.println("Información actualizada para: " + book.getName());
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Esperar a que todos los hilos finalicen antes de continuar
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Registrar la actualización masiva en el historial
        history.add("Actualización masiva de información completa.");
        System.out.println("Actualización masiva de información completa.");
    }

    // Método sincronizado para obtener un libro por índice
    public synchronized Book getBookByIndex(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        } else {
            System.out.println("Índice fuera de rango.");
            return null;
        }
    }
}
