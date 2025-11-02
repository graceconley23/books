package com.book.book.persistence;

import com.book.book.model.Book;
import java.io.IOException;
import java.util.List;

public interface CatalogDAO {
    Book[] getBooks() throws IOException;

    Book[] searchBooks(String searchText, double minPrice, double maxPrice,
                      List<String> genres, boolean inStock) throws IOException;

    Book getBook(String ISBN) throws IOException;

    Book createBook(Book book) throws IOException;

    Book updateBook(String ISBN, Book book) throws IOException;

    Book deleteBook(String ISBN) throws IOException;

    Book increaseStock(String ISBN, int quantity) throws IOException;

    Book decreaseStock(String ISBN, int quantity) throws IOException;
}
