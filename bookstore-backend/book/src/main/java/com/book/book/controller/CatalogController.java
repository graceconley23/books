package com.book.book.controller;

import com.book.book.model.Book;
import com.book.book.persistence.CatalogDAO;
import com.book.book.persistence.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("catalog")
public class CatalogController {
    private final CatalogDAO catalogDAO;
    Logger logger = Logger.getLogger(CatalogController.class.getName());

    private final UserDAO userDAO;

    public CatalogController(UserDAO userDAO, CatalogDAO catalogDAO) {
        this.userDAO = userDAO;
        this.catalogDAO = catalogDAO;
    }

    @GetMapping("/")
    public ResponseEntity<Book[]> searchBooks(@RequestParam(defaultValue = "") String text,
                                              @RequestParam(required = false, defaultValue="0") int minPrice,
                                              @RequestParam(required = false, defaultValue = "0") int maxPrice,
                                              @RequestParam(required = false, defaultValue = "false") boolean checkStock,
                                              @RequestParam(required = false) List<String> genres) {
        try {
            if (genres == null) {
                genres = new ArrayList<>();
            }
            logger.info("GET catalog/? searchBooks -- text: " + text + " Filters | minPrice: " + minPrice
                    + " maxPrice: " + maxPrice +  " checkStock: " + checkStock + " genres: "
                    + genres.toString());
            Book[] books = catalogDAO.searchBooks(text, minPrice, maxPrice, genres, checkStock);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Book[]> getAllBooks() throws IOException {
        logger.info("GET catalog");
        try {
            Book[] books = catalogDAO.getBooks();
            return new ResponseEntity<>(books, HttpStatus.OK);
        }  catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{ISBN}")
    public ResponseEntity<Book> getBook(@PathVariable String ISBN) throws IOException {
        logger.info("GET catalog/" + ISBN);
        try {
            Book ret = catalogDAO.getBook(ISBN);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws IOException {
        logger.info("POST catalog/" + book.toString());

        try {
            Book ret = catalogDAO.createBook(book);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{ISBN}")
    public ResponseEntity<Book> updateBook(@PathVariable String ISBN, @RequestBody Book book) throws IOException {
        logger.info("PUT catalog/" + ISBN + book.toString());
        try {
            Book ret = catalogDAO.updateBook(ISBN, book);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }  catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{ISBN}")
    public ResponseEntity<Book> deleteBook(@PathVariable String ISBN) throws IOException {
        logger.info("DELETE catalog/" + ISBN);
        try {
            Book ret = catalogDAO.deleteBook(ISBN);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/restock/{ISBN}/{amount}")
    public ResponseEntity<Book> increaseStock(@PathVariable String ISBN, @PathVariable int amount) throws IOException {
        logger.info("PUT catalog/restock" + ISBN);
        try {
            Book ret = catalogDAO.increaseStock(ISBN, amount);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/restock/{ISBN}/{amount}")
    public ResponseEntity<Book> decreaseStock(@PathVariable String ISBN, @PathVariable int amount) throws IOException {
        logger.info("DELETE catalog/restock" + ISBN);
        try {
            Book ret = catalogDAO.decreaseStock(ISBN, amount);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
