package com.book.book.model;

import java.util.Set;

public class Book {
    private String title;
    private String author;
    private String series;
    private String ISBN;
    private int quantity;
    private double price;
    private Set<String> genres;
    private int numberInSeries;
    private int numberOfPages;

    public Book(String title, String author, String series, String ISBN,
                int quantity, double price, Set<String> genres, int numberInSeries, int numberOfPages) {
        this.title = title;
        this.author = author;
        this.series = series;
        this.ISBN = ISBN;
        this.quantity = quantity;
        this.price = price;
        this.genres = genres;
        this.numberInSeries = numberInSeries;
        this.numberOfPages = numberOfPages;
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

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }

    public boolean outOfStock() {
        return quantity <= 0;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public boolean addGenre(String genre) {
        return genres.add(genre);
    }

    public boolean removeGenre(String genre) {
        return genres.remove(genre);
    }

    public boolean isGenre(String genre) {
        return genres.contains(genre);
    }

    public int getNumberInSeries() {
        return numberInSeries;
    }

    public void setNumberInSeries(int numberInSeries) {
        this.numberInSeries = numberInSeries;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Title: " + title + " By: " + author + " Series: " + series +
                " ISBN: " + ISBN + " Quantity: " + quantity + "  Price: "
                + price +  " Genres: " + genres.toString() + "numberInSeries: "
                + numberInSeries + "numberOfPages: " + numberOfPages;
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
