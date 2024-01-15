package com.example.proyectomultihilos.model.domain;

import java.sql.Date;
import java.util.Objects;

public class Book {
    private int isbn;
    private String name;
    private String author;
    private boolean available;

    /*fechas de prestamos y devoluciones*/

    /*private Date loanDate;

    private Date returnDate;*/

    public Book() {
    }

    public Book(int isbn, String name, String author, boolean available) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.available = available;
       /* this.loanDate=loanDate;
        this.returnDate=returnDate;*/
    }

    public Book(String title) {
        this.name=name;
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

   /* public Date getLoanDate() {
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
    }*/


}

