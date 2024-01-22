package com.example.proyectomultihilos.model.domain;

import java.sql.Date;
import java.util.Objects;

public class Book {
    // Atributos de la clase Book
    private int isbn;         // ISBN del libro
    private String name;      // Nombre del libro
    private String author;    // Autor del libro
    private boolean available; // Disponibilidad del libro

    /* Fechas de préstamos y devoluciones
     * Descomentar las siguientes líneas si se desea incluir funcionalidad de fechas
     * private Date loanDate;
     * private Date returnDate;
     */

    // Constructor por defecto
    public Book() {
    }

    // Constructor con parámetros principales
    public Book(int isbn, String name, String author, boolean available) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.available = available;
       /* this.loanDate=loanDate;
        this.returnDate=returnDate;*/
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

    // Métodos adicionales si se descomenta la funcionalidad de fechas

    /*
    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    */

    // Métodos adicionales si se desea personalizar el comportamiento de la clase

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, name);
    }
    */
}
