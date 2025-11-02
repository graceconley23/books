package com.book.book.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.book.book.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CatalogFileDAO implements CatalogDAO {
    public File file;
    public ObjectMapper objectMapper;
    private TreeMap<String, Book> bookMap;

    public CatalogFileDAO(@Value("${books.file}") String filename, ObjectMapper objectMapper) {
        System.out.println("Filename: " + filename);
        this.file = new File(filename);
        this.objectMapper = objectMapper;
        loadFromFile();
    }

    public Set<String> getGenres() {
        TreeSet<String> genres = new TreeSet<>();
        for (Book book : bookMap.values()) {
            genres.addAll(book.getGenres());
        }
        return genres;
    }

    private void loadFromFile() {
        this.bookMap = new TreeMap<>();
        try {
            Book[] booksArray = objectMapper.readValue(file, Book[].class);

            for (Book book : booksArray) {
                this.bookMap.put(book.getISBN(), book);
            }
        } catch (IOException e) {
            System.out.println("No books to load");
        }
    }

    private void writeToFile() throws IOException {
        objectMapper.writeValue(file, getBooksArray());
    }

    private Book[] getBooksArray() {
        ArrayList<Book> temp = new ArrayList<>(bookMap.values());

        Book[] ret = new Book[temp.size()];
        temp.toArray(ret);
        return ret;
    }

    // TODO sort by author, series, title
    private ArrayList<Book> getBooksArrayByText(String searchText) {
        ArrayList<Book> temp = new ArrayList<>();

        for (Book book : bookMap.values()) {
            if (book.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                temp.add(book);
            } else if (book.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                temp.add(book);
            } else if (book.getSeries().toLowerCase().contains(searchText.toLowerCase())) {
                temp.add(book);
            }
        }

        return temp;
    }

    private boolean matchesFilter(Book book, double minPrice, double maxPrice, List<String> genres, boolean checkInStock) {
        boolean removeFlag;

        if (minPrice != 0 && book.getPrice() < minPrice) {
            return false;
        }
        if (maxPrice != 0 && book.getPrice() > maxPrice) {
            return false;
        }
        if (!genres.isEmpty()) {
            removeFlag = true;
            for (String genre : genres) {
                if (book.isGenre(genre)) {
                    removeFlag = false;
                    break;
                }
            }
            if (removeFlag) {
                return false;
            }
        }
        return (!checkInStock || !book.outOfStock()); // final condition
    }

    private Book[] filterSearch(ArrayList<Book> books, double minPrice, double maxPrice,
                                List<String> genres, boolean checkInStock) {
        boolean checkingGenres = !genres.isEmpty();
        boolean removeFlag;

        books.removeIf(book -> !matchesFilter(book, minPrice, maxPrice, genres, checkInStock));

        Book[] ret =  new Book[books.size()];
        return books.toArray(ret);
    }

    @Override
    public Book[] getBooks() throws IOException {
        return getBooksArray();
    }

    @Override
    public Book[] searchBooks(String searchText, double minPrice, double maxPrice,
                             List<String> genres, boolean inStock) throws IOException {
        return filterSearch(getBooksArrayByText(searchText), minPrice, maxPrice, genres, inStock);
    }

    @Override
    public Book getBook(String ISBN) throws IOException {
        return bookMap.get(ISBN);
    }

    @Override
    public Book createBook(Book book) throws IOException {
        if (bookMap.containsKey(book.getISBN())) {
            return null;
        }
        bookMap.put(book.getISBN(), book);
        writeToFile();
        return book;
    }

    @Override
    public Book updateBook(String ISBN, Book book) throws IOException {
        if (!bookMap.containsKey(ISBN)) {
            return null;
        }
        book.setISBN(ISBN);
        bookMap.put(book.getISBN(), book);
        writeToFile();
        return book;
    }

    @Override
    public Book deleteBook(String ISBN) throws IOException {
        if (!bookMap.containsKey(ISBN)) {
            return null;
        }
        Book removed = bookMap.get(ISBN);
        bookMap.remove(ISBN);
        writeToFile();
        return removed;
    }

    @Override
    public Book increaseStock(String ISBN, int amount) throws IOException {
        if (!bookMap.containsKey(ISBN)) {
            return null;
        }
        Book book = bookMap.get(ISBN);
        book.increaseQuantity(amount);
        writeToFile();
        return book;
    }

    @Override
    public Book decreaseStock(String ISBN, int amount) throws IOException {
        if (!bookMap.containsKey(ISBN)) {
            return null;
        }
        if (bookMap.get(ISBN).outOfStock()) {
            // can't decrease the stock of a book if there are already 0 copies
            return null;
        }
        Book book = bookMap.get(ISBN);
        book.decreaseQuantity(amount);
        if (book.getQuantity() < 0) {
            book.setQuantity(0);
        }
        writeToFile();
        return book;
    }

    @Override
    public int getStock(String ISBN) throws IOException {
        if (!bookMap.containsKey(ISBN)) {
            return -1;
        }
        return bookMap.get(ISBN).getQuantity();
    }
}
