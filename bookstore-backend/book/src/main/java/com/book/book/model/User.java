package com.book.book.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;
    private List<Book> bookshelf;
    private List<Book> shoppingCart;
    private int booksReadThisYear;
    private String profilePictureLink;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.bookshelf = new ArrayList<>();
        this.shoppingCart = new ArrayList<>();
        this.booksReadThisYear = 0;
        this.profilePictureLink = "Spoopy.png";
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBookshelf() {
        return this.bookshelf;
    }

    public Book addToBookshelf(Book book) {
        if (this.bookshelf.contains(book)) {
            Book current = bookshelf.get(bookshelf.indexOf(book));
            current.increaseQuantity(1);
            return current;
        } else {
            Book copy = book.copy();
            this.bookshelf.add(copy); // same book but quantity 1
            return copy;
        }
    }

    public Book removeFromBookshelf(Book book) {
        if (!this.bookshelf.contains(book)) {
            return null;
        } else {
            Book current = bookshelf.get(bookshelf.indexOf(book));
            if (current.getQuantity() == 1) {
                this.bookshelf.remove(book);
            } else {
                current.decreaseQuantity(1);
            }
            return current;
        }
    }

    public List<Book> getShoppingCart() {
        return this.shoppingCart;
    }

    // TODO check if book in stock
    public Book addToShoppingCart(Book book) {
        if (this.shoppingCart.contains(book)) {
            Book current = shoppingCart.get(shoppingCart.indexOf(book));
            current.increaseQuantity(1);
            return current;
        } else {
            Book copy = book.copy();
            this.shoppingCart.add(book.copy());
            return copy;
        }
    }

    // TODO check if book in stock
    public Book removeFromShoppingCart(Book book) {
        if (!this.shoppingCart.contains(book)) {
            return null;
        } else {
            Book current = shoppingCart.get(shoppingCart.indexOf(book));
            if (current.getQuantity() == 1) {
                this.shoppingCart.remove(book);
            } else {
                current.decreaseQuantity(1);
            }
            return current;
        }
    }

    public int getBooksReadThisYear() {
        return this.booksReadThisYear;
    }

    public void updateBooksReadThisYear(int quantity) {
        this.booksReadThisYear += quantity;
    }

    public void decreaseBooksReadThisYear(int quantity) {
        this.booksReadThisYear -= quantity;
    }

    public String getProfilePictureLink() {
        return this.profilePictureLink;
    }

    public void setProfilePictureLink(String newLink) {
        this.profilePictureLink = newLink;
    }

    // TODO
    public void buyShoppingCart() {

    }
}
