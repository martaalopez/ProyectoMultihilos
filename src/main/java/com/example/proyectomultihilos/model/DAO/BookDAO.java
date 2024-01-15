package com.example.proyectomultihilos.model.DAO;

import com.example.proyectomultihilos.conexion.ConnectionMySQL;
import com.example.proyectomultihilos.model.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BookDAO {
    private List<Book> books;

    public BookDAO() {
        this.books = new ArrayList<>();
    }

    public synchronized void addBook(Book book) {
        books.add(book);
        System.out.println("Libro agregado: " + book.getName());
        notify(); // Notificar a los consumidores que hay un nuevo libro disponible.
    }

    public synchronized Book borrowBook() {
        while (books.isEmpty()) {
            try {
                // Esperar si no hay libros disponibles.
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Book borrowedBook = books.remove(0);
        System.out.println("Libro prestado: " + borrowedBook.getName());
        return borrowedBook;
    }

    public synchronized void returnBook(Book book) {
        books.add(book);
        System.out.println("Libro devuelto: " + book.getName());
        notify(); // Notificar a los productores que hay un libro disponible para prestar.
    }

    public void updateBooksInfo() {
        List<Book> bookList = new ArrayList<>(books);

        List<Thread> threads = new ArrayList<>();
        for (Book book : bookList) {
            Thread thread = new Thread(() -> {
                synchronized (book) {

                    book.setName(book.getName() + " - Actualizado");

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

    /*private ConnectionMySQL connection;

    private ExecutorService executorService;

    public BookDAO() {
        this.connection = connection;
    }

    private Connection conn = null;

    public BookDAO(ConnectionMySQL conexionBD) {
        this.connection = connection;
        this.executorService = Executors.newCachedThreadPool();
    }



    public void guardar(Book o) {
        String sql = "INSERT INTO book (isbn, title, author, available ) " +
                "VALUES (?, ?, ?,?)";
        try {

            PreparedStatement pst = this.connection.getConnect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, o.getIsbn());
            pst.setString(2, o.getName());
            pst.setString(3, o.getAuthor());
            pst.setBoolean(4, true);


            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }

    public boolean sacarLibro(int isbn) {
        Future<Boolean> future = executorService.submit(() -> actualizarDisponibilidad(isbn, false));
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean devolverLibro(int isbn) {
        Future<Boolean> future = executorService.submit(() -> actualizarDisponibilidad(isbn, true));
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean actualizarDisponibilidad(int isbn, boolean disponibilidad) {
        String sql = "UPDATE book SET disponible = ? WHERE isbn = ?";
        try (Connection conn = connection.getConnect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setBoolean(1, disponibilidad);
            pst.setInt(2, isbn);
            int rowsAffected = pst.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void cerrar() {
        executorService.shutdown();
    }
    public void insertarLibrosEnParalelo(List<Book> libros) {
        executorService = Executors.newFixedThreadPool(libros.size());

        for (Book libro : libros) {
            executorService.submit(() -> guardar(libro));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<Book> obtenerLibrosDisponibles() {
        List<Book> librosDisponibles = new ArrayList<>();

        String sql = "SELECT * FROM book WHERE available = true";

        try (Connection conn = connection.getConnect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean available = rs.getBoolean("available");

                Book libro = new Book(isbn, title, author, available);
                librosDisponibles.add(libro);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return librosDisponibles;
    }


   */





