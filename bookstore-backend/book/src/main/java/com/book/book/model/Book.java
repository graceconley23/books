package com.book.book.model;

public class Book {
    private String title;
    private String author;
    private String series;
    private String ISBN;
    private int quantity;

    public Book(String title, String author, String series, String ISBN, int quantity) {
        this.title = title;
        this.author = author;
        this.series = series;
        this.ISBN = ISBN;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Title: " + title + " By: " + author + " Series: " + series +
                " ISBN: " + ISBN + " Quantity: " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.getISBN().equals(book.getISBN());
    }

    @Override
    public int hashCode() {
        return this.getISBN().hashCode();
    }
}
