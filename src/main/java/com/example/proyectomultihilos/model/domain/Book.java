package com.example.proyectomultihilos.model.domain;

public class Book {
    // Atributos de la clase Book
    private int isbn;         // ISBN del libro
    private String name;      // Nombre del libro
    private String author;    // Autor del libro
    private boolean available; // Disponibilidad del libro


    // Constructor por defecto
    public Book() {
    }

    // Constructor con parámetros principales
    public Book(int isbn, String name, String author, boolean available) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.available = available;

    }

    // Constructor alternativo para crear un libro solo con el título
    public Book(String title) {
        this.name = title;
    }

    // Métodos getter y setter para acceder y modificar los atributos

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}
