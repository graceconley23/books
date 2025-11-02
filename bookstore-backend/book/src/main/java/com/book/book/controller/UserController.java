package com.book.book.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.book.book.model.Book;
import com.book.book.model.User;
import com.book.book.persistence.CatalogDAO;
import com.book.book.persistence.UserDAO;

@RestController
@RequestMapping("account")
public class UserController {
    private final UserDAO userDAO;
    private final CatalogDAO catalogDAO;

    Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController(UserDAO userDAO, CatalogDAO catalogDAO) {
        this.userDAO = userDAO;
        this.catalogDAO = catalogDAO;
    }

    // @GetMapping("/")
    // public ResponseEntity<Book[]> searchBooks(@RequestParam(defaultValue = "") String text,
    //                                           @RequestParam(required = false, defaultValue="0") int minPrice,
    //                                           @RequestParam(required = false, defaultValue = "0") int maxPrice,
    //                                           @RequestParam(required = false, defaultValue = "false") boolean checkStock,
    //                                           @RequestParam(required = false) List<String> genres) {
    //     try {
    //         if (genres == null) {
    //             genres = new ArrayList<>();
    //         }
    //         logger.info("GET catalog/? searchBooks -- text: " + text + " Filters | minPrice: " + minPrice
    //                 + " maxPrice: " + maxPrice +  " checkStock: " + checkStock + " genres: "
    //                 + genres.toString());
    //         Book[] books = catalogDAO.searchBooks(text, minPrice, maxPrice, genres, checkStock);
    //         return new ResponseEntity<>(books, HttpStatus.OK);
    //     } catch (IOException e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Book[]> getCart(@PathVariable int id) throws IOException {
        logger.info("GET cart");
        try {
            User user = userDAO.getUser(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                Book[] books = userDAO.getCart(id);
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
        }  catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cart/{id}/{ISBN}")
    public ResponseEntity<Book> addBookToCart(@PathVariable int id, @PathVariable String ISBN) throws IOException {
        logger.info("POST cart/" + ISBN);

        try {
            Book ret = userDAO.addToCart(id, ISBN);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cart/{id}/{ISBN}")
    public ResponseEntity<Book> removeBookFromCart(@PathVariable int id, @PathVariable String ISBN) throws IOException {
        logger.info("DELETE cart/" + ISBN);
        try {
            Book ret = userDAO.removeFromCart(id, ISBN);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bookshelf/{id}/{ISBN}")
    public ResponseEntity<Book> addBookToBookshelf(@PathVariable int id, @PathVariable String ISBN) throws IOException {
        logger.info("PUT bookshelf/" + ISBN);
        try {
            Book ret = userDAO.addToBookshelf(id, ISBN);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/bookshelf/{id}/{ISBN}")
    public ResponseEntity<Book> removeBookFromBookshelf(@PathVariable int id, @PathVariable String ISBN) throws IOException {
        logger.info("DELETE bookshelf/" + ISBN);
        try {
            Book ret = userDAO.removeFromBookshelf(id, ISBN);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookshelf/{id}/{ISBN}")
    public ResponseEntity<Book[]> getBookshelf(@PathVariable int id, @PathVariable String ISBN) throws IOException {
        logger.info("GET account/" + ISBN);
        try {
            Book[] ret = userDAO.getBookshelf(id);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping("/{id}/{ISBN}")
    // public ResponseEntity<Book> updateBook(@PathVariable String ISBN, @RequestBody Book book) throws IOException {
    //     logger.info("PUT catalog/" + ISBN + book.toString());
    //     try {
    //         Book ret = catalogDAO.updateBook(ISBN, book);
    //         if (ret == null) {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //         return new ResponseEntity<>(ret, HttpStatus.OK);
    //     }  catch (IOException e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @DeleteMapping("/{ISBN}")
    // public ResponseEntity<Book> deleteBook(@PathVariable String ISBN) throws IOException {
    //     logger.info("DELETE catalog/" + ISBN);
    //     try {
    //         Book ret = catalogDAO.deleteBook(ISBN);
    //         if (ret == null) {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //         return new ResponseEntity<>(ret, HttpStatus.OK);
    //     }
    //     catch (IOException e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @PutMapping("/restock/{ISBN}/{amount}")
    // public ResponseEntity<Book> increaseStock(@PathVariable String ISBN, @PathVariable int amount) throws IOException {
    //     logger.info("PUT catalog/restock" + ISBN);
    //     try {
    //         Book ret = catalogDAO.increaseStock(ISBN, amount);
    //         if (ret == null) {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //         return new ResponseEntity<>(ret, HttpStatus.OK);
    //     } catch (IOException e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    // @DeleteMapping("/restock/{ISBN}/{amount}")
    // public ResponseEntity<Book> decreaseStock(@PathVariable String ISBN, @PathVariable int amount) throws IOException {
    //     logger.info("DELETE catalog/restock" + ISBN);
    //     try {
    //         Book ret = catalogDAO.decreaseStock(ISBN, amount);
    //         if (ret == null) {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //         return new ResponseEntity<>(ret, HttpStatus.OK);
    //     }
    //     catch (IOException e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
