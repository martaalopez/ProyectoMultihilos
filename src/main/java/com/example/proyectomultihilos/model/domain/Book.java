package com.example.proyectomultihilos.model.domain;

import java.util.Objects;

public class Book {

    private int isbn;
    private String name;
    private String author;
    private boolean available;

    public Book() {
    }

    public Book(int isbn, String name, String author, boolean available) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.available = available;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book libro = (Book) o;
        return isbn == libro.isbn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn=" + isbn +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                '}';
    }
}
