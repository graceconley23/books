package com.book.book.persistence;

import java.io.IOException;

import com.book.book.model.Book;
import com.book.book.model.User;

public interface UserDAO {
    User[] getUsers() throws IOException;

    User[] searchUser(String name) throws IOException;

    User getUser(int id) throws IOException;

    User createUser(User user) throws IOException;

    User updateUser(int id, User user) throws IOException;

    User deleteUser(int id) throws IOException;

    Book addToCart(int id, String ISBN) throws IOException;

    Book removeFromCart(int id, String ISBN) throws IOException;

    Book addToBookshelf(int id, String ISBN) throws IOException;

    Book removeFromBookshelf(int id, String ISBN) throws IOException;

    Book[] getBookshelf(int id) throws IOException;

    Book[] getCart(int id) throws IOException;

    int updateAmountRead(int id, int number) throws IOException;

    int getAmountRead(int id) throws IOException;

    Book[] checkoutCart(int id) throws IOException;
}