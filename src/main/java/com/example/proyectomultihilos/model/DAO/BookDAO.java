package com.example.proyectomultihilos.model.DAO;

import com.example.proyectomultihilos.model.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private List<Book> books;
    private List<String> history;

    public BookDAO() {
        this.books = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    /*agregar libros*/
    public synchronized void addBook(Book book) {
        books.add(book);
        System.out.println("Libro agregado: " + book.getName());
    }

    public synchronized void borrowBook(Book selectedBook) {
        if (selectedBook.isAvailable()) {
            selectedBook.setAvailable(false);
            history.add("Libro prestado: " + selectedBook.getName());
            System.out.println("Libro prestado: " + selectedBook.getName());
        } else {
            System.out.println("El libro ya está prestado.");
        }
    }

    public synchronized void returnBook(Book selectedBook) {
        selectedBook.setAvailable(true);
        history.add("Libro devuelto: " + selectedBook.getName());
        System.out.println("Libro devuelto: " + selectedBook.getName());
    }

    public void updateBooksInfo() {
        List<Book> bookList = new ArrayList<>(books);

        List<Thread> threads = new ArrayList<>();
        for (Book book : bookList) {
            Thread thread = new Thread(() -> {
                synchronized (book) {
                    book.setName(book.getName() + " - Actualizado");
                    history.add("Información actualizada para: " + book.getName());
                    System.out.println("Información actualizada para: " + book.getName());
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        history.add("Actualización masiva de información completa.");
        System.out.println("Actualización masiva de información completa.");
    }

    public synchronized Book getBookByIndex(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        } else {
            System.out.println("Índice fuera de rango.");
            return null;
        }
    }
}
