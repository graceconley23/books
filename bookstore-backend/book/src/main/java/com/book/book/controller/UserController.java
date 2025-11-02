package com.book.book.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        logger.info("PUT cart/" + ISBN);

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
        logger.info("PUT bookshelf/" + id + '/' + ISBN);
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

    @GetMapping("/bookshelf/{id}")
    public ResponseEntity<Book[]> getBookshelf(@PathVariable int id) throws IOException {
        logger.info("GET account/bookshelf/" + id);
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

    @GetMapping("/timesRead/{id}")
    public ResponseEntity<Integer>  getTimesRead(@PathVariable int id) throws IOException {
        logger.info("GET account/timesRead/" + id);
        try {
            int ret = userDAO.getAmountRead(id);
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/timesRead/{id}/{number}")
    public ResponseEntity<Integer> updateBooksReadThisYear(@PathVariable int id, @PathVariable int number) throws IOException {
        logger.info("PUT timesRead/" + id + "/" + number);
        try {
            int newCount = userDAO.updateAmountRead(id, number);
            return new ResponseEntity<>(newCount, HttpStatus.OK);
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

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> checkout(@PathVariable int id) throws IOException {
        logger.info("DELETE cart/" + id);
        try {
            Book[] ret = userDAO.checkoutCart(id);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (IOException e) {
            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", e.getMessage()));
        }
    }

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
