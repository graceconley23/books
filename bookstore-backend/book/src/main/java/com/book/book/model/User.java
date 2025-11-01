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

    private int getId() {
        return this.id;
    }

    private void setId(int id) {
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

    public boolean addToBookshelf(Book book) {
        if (this.bookshelf.contains(book)) {
            Book current = bookshelf.get(bookshelf.indexOf(book));
            current.increaseQuantity(1);
        } else {
            this.bookshelf.add(book);
        }
        return true;
    }

    public boolean removeFromBookshelf(Book book) {
        if (!this.bookshelf.contains(book)) {
            return false;
        } else {
            Book current = bookshelf.get(bookshelf.indexOf(book));
            if (current.getQuantity() == 1) {
                this.bookshelf.remove(book);
            } else {
                current.decreaseQuantity(1);
            }
            return true;
        }
    }

    public List<Book> getShoppingCart() {
        return this.shoppingCart;
    }

    public boolean addToShoppingCart(Book book) {
        if (this.shoppingCart.contains(book)) {
            Book current = shoppingCart.get(shoppingCart.indexOf(book));
            current.increaseQuantity(1);
        } else {
            this.shoppingCart.add(book);
        }
        return true;
    }

    public boolean removeFromShoppingCart(Book book) {
        if (!this.shoppingCart.contains(book)) {
            return false;
        } else {
            Book current = shoppingCart.get(shoppingCart.indexOf(book));
            if (current.getQuantity() == 1) {
                this.shoppingCart.remove(book);
            } else {
                current.decreaseQuantity(1);
            }
            return true;
        }
    }

    public int getBooksReadThisYear() {
        return this.booksReadThisYear;
    }

    public void increaseBooksReadThisYear(int quantity) {
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
}
